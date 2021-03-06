#!/usr/bin/env python
# coding: utf-8

# ## Import Libraries

# In[1]:


# Copyright (c) Facebook, Inc. and its affiliates. All Rights Reserved
import argparse
import glob
import multiprocessing as mp
import os
import time
import cv2
import tqdm

from detectron2.data.detection_utils import read_image
from detectron2.utils.logger import setup_logger

from demo.predictor import VisualizationDemo
from adet.config import get_cfg

# constants
WINDOW_NAME = "COCO detections"


# ## Input parameters

# In[2]:


def setup_cfg(args):
    # load config from file and command-line arguments
    cfg = get_cfg()
    cfg.merge_from_file(args.config_file)
    cfg.merge_from_list(args.opts)
    # Set score_threshold for builtin models
    cfg.MODEL.RETINANET.SCORE_THRESH_TEST = args.confidence_threshold
    cfg.MODEL.ROI_HEADS.SCORE_THRESH_TEST = args.confidence_threshold
    cfg.MODEL.FCOS.INFERENCE_TH_TEST = args.confidence_threshold
    cfg.MODEL.MEInst.INFERENCE_TH_TEST = args.confidence_threshold
    cfg.MODEL.PANOPTIC_FPN.COMBINE.INSTANCES_CONFIDENCE_THRESH = args.confidence_threshold
    cfg.freeze()
    return cfg


def get_parser():
    parser = argparse.ArgumentParser(description="Detectron2 Demo")
    parser.add_argument(
        "--config-file",
        default="configs/quick_schedules/e2e_mask_rcnn_R_50_FPN_inference_acc_test.yaml",
        metavar="FILE",
        help="path to config file",
    )
    parser.add_argument("--webcam", action="store_true", help="Take inputs from webcam.")
    parser.add_argument("--video-input", help="Path to video file.")
    parser.add_argument("--input", nargs="+", help="A list of space separated input images")
    parser.add_argument(
        "--output",
        help="A file or directory to save output visualizations. "
        "If not given, will show output in an OpenCV window.",
    )

    parser.add_argument(
        "--confidence-threshold",
        type=float,
        default=0.5,
        help="Minimum score for instance predictions to be shown",
    )
    parser.add_argument(
        "--opts",
        help="Modify config options using the command-line 'KEY VALUE' pairs",
        default=[],
        nargs=argparse.REMAINDER,
    )
    return parser    


# In[3]:


# import easydict 
# args = easydict.EasyDict({ 
#     "config_file": "configs/SOLOv2/R50_3x.yaml", 
#     "input": ["../../datasets/ruru.jpg"], 
#     "output": "results", 
#     "confidence_threshold": 0.5, 
#     "opts": ["MODEL.WEIGHTS", "../../models/SOLOv2_R50_3x.pth"] })

mp.set_start_method("spawn", force=True)
args = get_parser().parse_args()
logger = setup_logger()
logger.info("Arguments: " + str(args))

cfg = setup_cfg(args)

demo = VisualizationDemo(cfg)


# ## Run SOLOv2

# In[4]:


args.input = glob.glob(os.path.expanduser(args.input[0]))

for path in tqdm.tqdm(args.input, disable=not args.output):
    # use PIL, to be consistent with evaluation
    img = read_image(path, format="BGR")
    start_time = time.time()
    predictions, visualized_output = demo.run_on_image(img)


    out_filename = os.path.join(args.output, os.path.basename(path))
    
    visualized_output.save(out_filename)


# ## Visualize

# In[5]:


from matplotlib.pylab import plt
import numpy as np
from PIL import Image


# In[6]:


predictions['instances'][0].pred_masks


# In[7]:


# plt.imshow(predictions['instances'][0].pred_masks.to("cpu").numpy()[0])


# In[8]:


mask = predictions['instances'][0].pred_masks.to("cpu").numpy()[0]
nuki = img * np.expand_dims(mask, axis=2)


# In[9]:


# plt.imshow(nuki)
result = Image.fromarray(nuki)
result.save(out_filename)


# In[ ]:




