import flask
from flask import Flask, request, send_file
import numpy as np
from PIL import Image

from meme import _meme_image
from dl_utils import _det_image
from dl_utils import _sod_image
from dl_utils import _seg_image
from dl_utils import _gan_image

app = Flask(__name__)

# COMMON
@app.route("/ping")
def ping():
    return "PING"

# DET
@app.route("/det", methods = ["POST"])
def det_image():
    if request.method == "POST":
        print(flask.request)
        print(flask.request.data)
        print(flask.request.files)
        files = flask.request.files
        sFile = files["source"]
        
        sFilename = "samples/source_det.jpg"
        rFilename = "results/result_det.jpg"
        
        sImage = Image.open(sFile)
        sImage.save(sFilename)

        _det_image(sFilename, rFilename)
        return send_file(rFilename, mimetype="image/jpg")

# SEG
@app.route("/seg", methods = ["POST"])
def seg_image():
    if request.method == "POST":
        files = flask.request.files
        sFile = files["source"]
        
        sFilename = "samples/source_seg.jpg"
        rFilename = "results/result_seg.jpg"
        
        sImage = Image.open(sFile)
        sImage.save(sFilename)

        _seg_image(sFilename, rFilename)
        return send_file(rFilename, mimetype="image/jpg")
    
# SOD
@app.route("/sod", methods = ["POST"])
def sod_image():
    if request.method == "POST":
        files = flask.request.files
        sFile = files["source"]
        
        sFilename = "samples/source_sod.jpg"
        rFilename = "results/result_sod.jpg"
        
        sImage = Image.open(sFile)
        sImage.save(sFilename)

        _sod_image(sFilename, rFilename)
        return send_file(rFilename, mimetype="image/jpg")
    
# GAN
@app.route("/gan", methods = ["POST"])
def gan_image():
    if request.method == "POST":
        files = flask.request.files
        sFile = files["source"]
        mFile = files["mask"]
        
        sFilename = "samples/source_gan.jpg"
        mFilename = "samples/mask_gan.jpg"
        rFilename = "results/result_gan.jpg"
        
        sImage = Image.open(sFile)
        sImage.save(sFilename)
        #mImage = np.zeros((sImage.size[1], sImage.size[0], 3)).astype(np.uint8)
        #for x in range(100):
        #    for y in range(100):
        #        mImage[x][y][0]=255
        #mImage = Image.fromarray(mImage)
        mImage = Image.open(mFile)
        mImage.save(mFilename)

        _gan_image(sFilename, mFilename, rFilename)
        return send_file(rFilename, mimetype="image/jpg")
    
# MEME
@app.route("/meme", methods = ["POST"])
def meme_image():
    if request.method == "POST":
        files = flask.request.files
        sFile = files["source"]
        tFile = files["target"]
        
        sFilename = "inputs/source.jpg"
        tFilename = "inputs/target.jpg"
        rFilename = "results/result.jpg"
        
        sImage = Image.open(sFile)
        tImage = Image.open(tFile)
        sImage.save(sFilename)
        tImage.save(tFilename)

        _meme_image(sFilename, tFilename, rFilename)
        return send_file(rFilename, mimetype="image/jpg")

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=47000, debug=True)
