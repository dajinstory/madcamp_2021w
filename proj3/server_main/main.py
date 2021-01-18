import flask
from flask import Flask, request, send_file
import requests
import json

PORT = 5000
app = Flask(__name__)

@app.route("/")
def hello():
    return "11Hello World!"

@app.route("/ping", methods = ["GET"])
def ping():
    response = requests.get(url="http://localhost:47000/ping")
    return response.text

# DET
@app.route("/det", methods = ["POST"])
def det_image():
    req = flask.request
    url = "http://localhost:47000/det"
    files = {'source': req.files["source"]}
    res = requests.post(url, files=files)
    return "asdf"

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=PORT, debug=True)
