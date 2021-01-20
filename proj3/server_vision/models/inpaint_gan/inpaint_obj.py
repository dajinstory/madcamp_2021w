import imageio
import numpy as np
from argparse import ArgumentParser
import easydict
import torch

from .trainer import Trainer
from .utils.tools import get_config

args = easydict.EasyDict({ 
    "config": "models/inpaint_gan/configs/config.yaml", 
    "model_path": "models/inpaint_gan/torch_model.p", 
})

def inpaint_blank(image, mask):
    device = torch.device("cuda")
    config = get_config(args.config)
    
    trainer = Trainer(config)
    trainer.load_state_dict(load_weights(args.model_path, device), strict=False)
    trainer.eval()
    
    image = torch.FloatTensor(image).permute(2, 0, 1).unsqueeze(0).cuda()
    mask = (torch.FloatTensor(mask[:, :, 0]) / 255).unsqueeze(0).unsqueeze(0).cuda()

    x = (image / 127.5 - 1) * (1 - mask).cuda()
    with torch.no_grad():
        _, result, _ = trainer.netG(x, mask)

    return upcast(result[0].permute(1, 2, 0).detach().cpu().numpy())


def load_weights(path, device):
    model_weights = torch.load(path)
    return {
        k: v.to(device)
        for k, v in model_weights.items()
    }


def upcast(x):
    return np.clip((x + 1) * 127.5 , 0, 255).astype(np.uint8)
