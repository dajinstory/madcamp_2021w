const router = require('express').Router();
const multer = require('multer');
const http = require('http');
const url = require('url');
const fs = require('fs');


// directory setting
fs.readdir("images", (err) => {
	if (err) {
		console.error("images 폴더가 없어 images 폴더를 생성합니다");
		fs.mkdirSync("images");
	}
});
fs.readdir("images/uploads", (err) => {
	if (err) {
		console.error("uploads 폴더가 없어 uploads 폴더를 생성합니다");
		fs.mkdirSync("images/uploads");
	}
});


// multer setting
const upload = multer({
  storage: multer.diskStorage({
    // set a localstorage destination
    destination: (req, file, cb) => {
      cb(null, 'images/uploads/');
    },
    // convert a file name
    filename: (req, file, cb) => {
      cb(null, new Date().valueOf() +file.originalname);
    },
  }),
});


// upload image
router.post("/upload", upload.single('file'), (req, res) => {
	console.log(req.file);
	res.json({url: "uploads/"+req.file.filename})
});

module.exports = router;


