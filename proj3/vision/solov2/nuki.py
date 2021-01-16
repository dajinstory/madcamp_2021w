#!/usr/bin/env python
# coding: utf-8

# Copyright (c) Facebook, Inc. and its affiliates. All Rights Reserved
import argparse
import glob
import multiprocessing as mp
import os
import time
import cv2
import tqdm
import easydict 
import numpy as np

from detectron2.data.detection_utils import read_image
from detectron2.utils.logger import setup_logger

from demo.predictor import VisualizationDemo
from adet.config import get_cfg

# constants
WINDOW_NAME = "COCO detections"

def setup_cfg(args):
    # load config from file and command-line arguments
    cfg = get_cfg()
    cfg.MODEL.DEVICE="cpu"
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

def nuki_faces(img, bboxs):
    # set arguments
    args = easydict.EasyDict({ 
        "config_file": "solov2/configs/SOLOv2/R50_3x.yaml", 
        "input": ["jsm.jpg"], 
        "output": "results", 
        "confidence_threshold": 0.5, 
        "opts": ["MODEL.WEIGHTS", "solov2/weights/SOLOv2_R50_3x.pth"] })

    mp.set_start_method("spawn", force=True)
    logger = setup_logger()
    logger.info("Arguments: " + str(args))
    cfg = setup_cfg(args)

    # create demo model
    demo = VisualizationDemo(cfg)

    # run
    masks = []
    for bbox in bboxs:
        img_crop = np.asarray(img.crop(tuple(bbox[:4])))
  
        predictions, visualized_output = demo.run_on_image(img_crop)
        masks.append(predictions['instances'][0].pred_masks[0].to('cpu').numpy())

    return masks

