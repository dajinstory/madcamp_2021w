const router = require('express').Router();
const multer = require('multer');
const http = require('http');
const url = require('url');
const fs = require('fs');

// multer setting
const upload = multer({
  storage: multer.diskStorage({
    // set a localstorage destination
    destination: (req, file, cb) => {
      cb(null, '../images/uploads/');
    },
    // convert a file name
    filename: (req, file, cb) => {
      cb(null, new Date().valueOf() + path.extname(file.originalname));
    },
  }),
});

// upload image
router.post("/upload", (req, res, next) => {
  upload(req, res, function(err){
    if( err instanceof multer.MulterError){
      return next(err);
    }else if (err){
      return next(err);
    }
    console.log(req.file.originalname)
    console.log(req.file.filename)
    console.log(req.file.size)

    return res.json({success:1, filename:req.file.filename})
  });
});

// Find All
router.get('/', (req, res) => {
});

module.exports = router;
