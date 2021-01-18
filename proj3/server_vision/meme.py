from PIL import Image
import numpy as np
from matplotlib.pylab import plt
from numpy import arccos, array
from numpy.linalg import norm
import math
import cv2
import imageio
import numpy as np
import torch

from models.mtcnn import detect_faces, show_bboxes
from models.solov2 import nuki_faces
from models.face_parsing import seg_faces
from models.inpaint_gan import inpaint_blank

### Functions for fitting position
def getCenter(matrix):
    center_x = 0.0
    center_y = 0.0
    region = 0.0
    for y in range(matrix.shape[0]):
        for x in range(matrix.shape[1]):
            if matrix[y][x]:
                center_x += x
                center_y += y
                region += 1.0

    if region < 1.0:
        center_x = -1.0
        center_y = -1.0
    else:
        center_x /= region
        center_y /= region

    return center_x, center_y

def getLandmarks(masks):
    landmarks = []
    for mask in masks:
        mask_leye = (mask == 4)
        mask_reye = (mask == 5)
        mask_nose = (mask == 10)
        mask_mouth = (mask == 11)
        mask_upper_lip = (mask == 12)
        mask_under_lip = (mask == 13)

        landmark = {}
        landmark["leye"]=np.array(getCenter(mask_leye))
        landmark["reye"]=np.array(getCenter(mask_reye))
        landmark["nose"]=np.array(getCenter(mask_nose))
        landmark["mouth"]=np.array(getCenter(mask_mouth))
        landmark["upper_lip"]=np.array(getCenter(mask_upper_lip))
        landmark["under_lip"]=np.array(getCenter(mask_under_lip))
        landmarks.append(landmark)
    
    return landmarks

def resizeLandmarks(landmarks, landmarks_size):
    for i, (landmark, landmark_size) in enumerate(zip(landmarks, landmarks_size)):
        for key in landmark.keys():
            if np.sum(landmark[key]) > 0:
                landmark[key] *= np.array(landmark_size) / 512.0
    return landmarks

def resizeMasks(masks, masks_size):
    newMasks = []
    for (mask, mask_size) in zip(masks, masks_size):
        mask = Image.fromarray(mask)
        mask = mask.resize(mask_size, resample=Image.BICUBIC)
        newMasks.append(np.asarray(mask))
    return newMasks

def theta(v, u): 
    v /= np.linalg.norm(v)
    u /= np.linalg.norm(u)
    angle = np.arccos(np.dot(v, u))
    return angle

def getFaceDir(landmark):
    if landmark["under_lip"][0] > 0:
        vector = (landmark["leye"] + landmark["reye"])/2 - landmark["under_lip"]
    elif landmark["upper_lip"][0] > 0:
        vector = (landmark["leye"] + landmark["reye"])/2 - landmark["upper_lip"]
    elif landmark["mouth"][0] > 0:
        vector = (landmark["leye"] + landmark["reye"])/2 - landmark["mouth"]
    else:
        vector = (landmark["leye"] + landmark["reye"])/2 - landmark["nose"]
    return vector / np.linalg.norm(vector)

def checkHeadDir(mask, landmark):
    xc, yc = getCenter(np.asarray(mask))
    if xc < landmark["nose"][0]:
        return "Left"
    else:
        return "Right"

def getRefinedMask(sourceImg, sourceMask, sourceLandmark, targetVector, target_size, flip_check):
    print(flip_check)
    # source vector
    sourceVector = getFaceDir(sourceLandmark)
    
    # check if flip necessary
    print(sourceVector, targetVector, theta(sourceVector, targetVector))
    if flip_check: 
        targetVector[0] *= -1.0
        print(sourceVector, targetVector, theta(sourceVector, targetVector))

    xc, yc = sourceLandmark["nose"].astype(int)
    h, w = sourceImg.size[:2]
    resize_ratio = target_size / np.array(sourceImg.size)
    
    # Rotate
    degree = 180 / np.pi * theta(sourceVector, targetVector) * (1.0 if sourceVector[0] > targetVector[0] else -1.0)
#     degree /= 1.2
    image = sourceImg.rotate(degree, resample=Image.BICUBIC, center=(xc, yc))
    mask = sourceMask.rotate(degree, resample=Image.BICUBIC, center=(xc, yc))
    
    # Crop and Resize
    nwr = int(min(image.size[0]-xc-1, xc-1))
    nhr = int(min(image.size[1]-yc-1, yc-1))
    image = image.crop((xc-nwr, yc-nhr, xc+nwr, yc+nhr))
    mask = mask.crop((xc-nwr, yc-nhr, xc+nwr, yc+nhr))

    image = image.resize(tuple((np.array(image.size) * resize_ratio).astype(int)))
    mask = mask.resize(tuple((np.array(mask.size) * resize_ratio).astype(int)))
    
    # Flip if necessary
    if flip_check:
        image = image.transpose(method=Image.FLIP_LEFT_RIGHT)
        mask = mask.transpose(method=Image.FLIP_LEFT_RIGHT)
    
    return image, mask



def _meme_image(sFilename, tFilename, rFilename):
    ### 1. Source Image

    # Get BBoxs
    #myimg_orig = Image.open('samples/yjs2.jpg')
    myimg_orig = Image.open(sFilename)
    mybboxs, mylandmarks = detect_faces(myimg_orig)

    # Convert to Nuki Boxs
    mynboxs = []
    for mybbox in mybboxs:
        x0, y0, x1, y1 = mybbox[:4]
        xc = (x0+x1)/2
        yc = (y0+y1)/2
        wr = (x1-x0)/2 * 2
        hr = (y1-y0)/2 * 2
        mynboxs.append((
            int(max(0,xc-wr)), 
            int(max(0,yc-hr*2)), 
            int(min(xc+wr, myimg_orig.size[0])), 
            int(min(yc+hr, myimg_orig.size[1]))))

    w = mynboxs[0][2] - mynboxs[0][0]
    h = mynboxs[0][3] - mynboxs[0][1]
    mymasks_all = np.array(seg_faces(myimg_orig, mynboxs))
    mymasks = (mymasks_all > 0) & (mymasks_all != 14) & (mymasks_all != 16)
    mymasks = resizeMasks(mymasks, [(w,h)])
    mylandmarks = getLandmarks(mymasks_all)
    mylandmarks = resizeLandmarks(mylandmarks, [(w,h)])

    # Source Info
    mymask = Image.fromarray(mymasks[0])
    myimg = myimg_orig.crop(tuple(mynboxs[0])).resize((mymask.size))
    mylandmark = mylandmarks[0]
    sourceVector = getFaceDir(mylandmark)
    
    
    ### 2. Target Image

    # Get BBoxs
    #img = Image.open('samples/pms.jpg')#.crop((600,200,800,700))
    img = Image.open(tFilename)
    img = Image.fromarray(np.asarray(img)[:,:,:3])
    bboxs, landmarks = detect_faces(img)

    # Convert to Nuki Boxs
    nboxs = []
    for bbox in bboxs:
        x0, y0, x1, y1 = bbox[:4]
        xc = (x0+x1)/2
        yc = (y0+y1)/2
        wr = (x1-x0)/2 * 2
        hr = (y1-y0)/2 * 2
        nboxs.append((
            int(max(0, xc-wr)), 
            int(max(0, yc-hr)), 
            int(min(xc+wr, img.size[0])), 
            int(min(yc+hr, img.size[1]))
        ))

    sizes = [(nb[2]-nb[0], nb[3]-nb[1]) for nb in nboxs]
    masks_all = np.array(seg_faces(img, nboxs))
    masks = (masks_all > 0) & (masks_all != 14) & (masks_all != 16)
    masks = resizeMasks(masks, sizes)
    landmarks = getLandmarks(masks_all)
    landmarks = resizeLandmarks(landmarks, sizes)


    ### 3. Replace Face
    my_img = np.asarray(myimg)
    orig_img = np.asarray(img)
    result_img = np.asarray(img).copy()
    inpaint_mask = np.zeros(result_img.shape[:2])

    zip_list = zip(nboxs, masks, landmarks)
    zip_list = sorted(zip_list, key=lambda x : x[1].sum())
    for nbox, mask, landmark in zip_list:

        # Target Face Region
        xc, yc = nbox[:2]+landmark["nose"].astype(int)
        w = nbox[2]-nbox[0]
        h = nbox[3]-nbox[1]
        targetVector = getFaceDir(landmark)

        # Erase Target Region
        for y in range(nbox[1], nbox[3]):
            for x in range(nbox[0], nbox[2]):
                if mask[y-nbox[1]][x-nbox[0]] == 1 and inpaint_mask[y][x] == 0: # status : None
                    inpaint_mask[y][x] = 1 # status : blank

        # Change Source Face Angle, Size
        flip_check = checkHeadDir(mask, landmark) != checkHeadDir(mymask, mylandmark)
        face_img, face_mask = getRefinedMask(myimg, mymask, mylandmark, targetVector, (w,h), flip_check)

        # Adjust Mask
        face_img = np.asarray(face_img).copy()
        face_mask = np.asarray(face_mask)
        face_mask = np.expand_dims(face_mask, 2)
        face_img *= face_mask

        # Sticker Source Image to Target Region
        for y in range(face_img.shape[0]):
            ny = yc - int(face_img.shape[0]/2) + y
            if ny < 0 or ny >= result_img.shape[0]:
                continue
            for x in range(face_img.shape[1]):
                nx = xc - int(face_img.shape[1]/2) + x
                if nx < 0 or nx >= result_img.shape[1]:
                    continue

                if np.sum(face_img[y][x]) == 0:
                    continue
                result_img[ny][nx] = face_img[y][x]
                inpaint_mask[ny][nx] = -1 # status : filled


    ### 4. Inpaint blank

    inpaint_mask = (inpaint_mask == 1)
#     plt.imshow(inpaint_mask)
#     plt.show()
#     plt.imshow(result_img)
#     plt.show()
#     plt.imshow(result_img * np.expand_dims(inpaint_mask==False,2))
#     plt.show()

    inpaint_mask = (inpaint_mask == 1).astype(float) * 255
    inpaint_mask = np.asarray(Image.fromarray(inpaint_mask).resize((512,512), resample=Image.BICUBIC))
    inpaint_mask = np.expand_dims(inpaint_mask, 2)
    result_img = np.asarray(Image.fromarray(result_img).resize((512,512), resample=Image.BICUBIC))

    res = inpaint_blank(result_img, inpaint_mask)

    res_img = Image.fromarray(res)
    res_img = res_img.resize(img.size)
    #res_img.save("results/result_sample.jpg")
    res_img.save(rFilename)

    return rFilename
    
