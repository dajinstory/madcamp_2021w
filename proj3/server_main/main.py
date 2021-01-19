import flask
from flask import Flask, request, send_file, send_from_directory
import requests
import json
from PIL import Image
import io

PORT = 5000
app = Flask(__name__, static_folder = "results")

@app.route("/")
def hello():
    return "11Hello World!"

@app.route("/ping", methods = ["GET", "POST"])
def ping():
    if flask.request.method == "POST":
        print(flask.request)
    response = requests.get(url="http://localhost:47000/ping")
    return json.dumps({"text":response.text})

# DET
@app.route("/det", methods = ["POST"])
def det_image():
    # request
    req = flask.request

    # create new request
    url = "http://localhost:47000/det"
    files = {'source': req.files["source"]}
    res = requests.post(url, files=files)
      
    # postprocessing
    filename = "results/result_det.jpg"
    Image.open(io.BytesIO(res.content)).save(filename)
      
    return send_from_directory("results", "result_det.jpg")

# SEG
@app.route("/seg", methods = ["POST"])
def seg_image():
    # request
    req = flask.request

    # create new request
    url = "http://localhost:47000/seg"
    files = {'source': req.files["source"]}
    res = requests.post(url, files=files)
    
    # postprocessing
    filename = "results/result_seg.jpg"
    Image.open(io.BytesIO(res.content)).save(filename)
    return send_file(filename, mimetype="image/jpg")

# SOD
@app.route("/sod", methods = ["POST"])
def sod_image():
    # request
    req = flask.request

    # create new request
    url = "http://localhost:47000/sod"
    files = {'source': req.files["source"]}
    res = requests.post(url, files=files)
    
    # postprocessing
    filename = "results/result_sod.jpg"
    Image.open(io.BytesIO(res.content)).save(filename)
    return send_file(filename, mimetype="image/jpg")

# GAN
@app.route("/gan", methods = ["POST"])
def gan_image():
    # request
    req = flask.request

    # create new request
    url = "http://localhost:47000/gan"
    files = {
            'source': req.files["source"], 
            'mask': req.files["mask"]
            }
    res = requests.post(url, files=files)
    
    # postprocessing
    filename = "results/result_gan.jpg"
    Image.open(io.BytesIO(res.content)).save(filename)
    return send_file(filename, mimetype="image/jpg")

# MEME
@app.route("/meme", methods = ["POST"])
def meme_image():
    # request
    req = flask.request

    # create new request
    url = "http://localhost:47000/meme"
    files = {
            'source': req.files["source"], 
            'target': req.files["target"]
            }
    res = requests.post(url, files=files)
    
    # postprocessing
    filename = "results/result_meme.jpg"
    Image.open(io.BytesIO(res.content)).save(filename)
    return send_file(filename, mimetype="image/jpg")

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=PORT, debug=True)
