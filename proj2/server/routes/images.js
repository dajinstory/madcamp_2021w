const router = require('express').Router();
const multer = require('multer');
const http = require('http');
const url = require('url');
const fs = require('fs');
const crypto = require('crypto')

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

var _storage = multer.diskStorage({

	destination: 'images/uploads/',
	filename: function(req, file, cb) {
		return crypto.pseudoRandomBytes(16, function(err, raw) {
			if(err) {
				return cb(err);
			}
			//return cb(null, ""+(raw.toString('hex')) + (path.extname(file.originalname)));
			return cb(null, file.originalname);
		});
	}
});
//업로드
router.post('/upload', 
	multer({
		storage: _storage
	}).single('upload'), function (req, res) {

	try {

		let file = req.file;
		//const files = req.files;
		let originalName = '';
		let fileName = '';
		let mimeType = '';
		let size = 0;

		if(file) {
			originalName = file.originalname;
			filename = file.fileName;//file.fileName
			mimeType = file.mimetype;
			size = file.size;
			console.log("execute"+fileName);
		} else{ 
			console.log("request is null");
		}

	} catch (err) {

		console.dir(err.stack);
	}

	console.log(req.file);
	console.log(req.body);
	res.redirect("/uploads/" + req.file.originalname);//fileName

	return res.status(200).end();

});

module.exports = router;


