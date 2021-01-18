import flask
from flask import Flask, request, send_file
import requests

PORT = 5000
app = Flask(__name__)

@app.route("/")
def hello():
    return "11Hello World!"

@app.route("/det", methods = ["GET"])
def det_image():
    files = flask.request.files
    requests.get(url="http://localhost:47000/det",)
    return "asdf"

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=PORT, debug=True)
