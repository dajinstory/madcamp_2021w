const router = require('express').Router();
const Shop = require('../models/shop');

// Find All
router.get('/', (req, res) => {
  console.log("GET Shop ALL")
  Shop.findAll()
    .then((shops) => {
      console.log("TRY")
	  if (!shops.length) return res.status(404).send({ err: 'Shop not found' });
      console.log("SUCCESS")
	  //res.send(`find successfully: ${shops}`);
	  res.json(shops);
	})
    .catch(err => res.status(500).send(err));
});

// Find One by name
router.get('/name/:name/', (req, res) => {
  console.log("GET Shop Single");
  Shop.findOneByName(req.params.name)
    .then((shop) => {
      console.log("TRY");
      if (!shop) return res.status(404).send({ err: 'Shop not found' });
      console.log("SUCCESS");
	  //res.send(`findOne successfully: ${shop}`);
	  res.json(shop);
    })
    .catch(err => res.status(500).send(err));
});

// Find One by id
router.get('/id/:_id/', (req, res) => {
  console.log("GET Shop Single");
  Shop.findOneById(req.params._id)
    .then((shop) => {
      console.log("TRY");
      if (!shop) return res.status(404).send({ err: 'Shop not found' });
      console.log("SUCCESS");
	  //res.send(`findOne successfully: ${shop}`);
	  res.json(shop);
    })
    .catch(err => res.status(500).send(err));
});

// Create new shop document
router.post('/', (req, res) => {
  Shop.create(req.body)
    .then(shop => res.send(shop))
    .catch(err => res.status(500).send(err));
});

// Update by id
router.put('/id/:_id/', (req, res) => {
  Shop.updateById(req.params._id, req.body)
    .then(shop => res.send(shop))
    .catch(err => res.status(500).send(err));
});

// Delete by id
router.delete('/id/:_id/', (req, res) => {
  Shop.deleteById(req.params._id)
    .then(() => res.sendStatus(200))
    .catch(err => res.status(500).send(err));
});

module.exports = router;
