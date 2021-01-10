const router = require('express').Router();
const multer = require('multer');

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

// Create new image document
router.post('/uploads', upload.single('img'), (req, res) => {
  Image.create(req.body)
    .then(image => res.send(image))
    .catch(err => res.status(500).send(err));
});

// Find All
router.get('/', (req, res) => {
  Image.findAll()
    .then((images) => {
      if (!images.length) return res.status(404).send({ err: 'Image not found' });
      res.send(`find successfully: ${images}`);
    })
    .catch(err => res.status(500).send(err));
});

// Find One by id
router.get('/imageid/:imageid', (req, res) => {
	Image.findOneByImageId(req.params.imageid)
		.then((image) => {
			if (!image) return res.status(404).send({ err: 'Image not found' });
				res.send(`findOne successfully: ${image}`);
		})
		.catch(err => res.status(500).send(err));
});
module.exports = router;
