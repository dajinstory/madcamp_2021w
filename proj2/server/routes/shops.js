const router = require('express').Router();
const Shop = require('../models/shop');

// Find All
router.get('/', (req, res) => {
  Shop.findAll()
    .then((shops) => {
      if (!shops.length) return res.status(404).send({ err: 'Shop not found' });
      res.send(`find successfully: ${shops}`);
    })
    .catch(err => res.status(500).send(err));
});

// Find One by id
router.get('/shopid/:shopid', (req, res) => {
  Shop.findOneByShopId(req.params.shopid)
    .then((shop) => {
      if (!shop) return res.status(404).send({ err: 'Shop not found' });
      res.send(`findOne successfully: ${shop}`);
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
router.put('/shopid/:shopid', (req, res) => {
  Shop.updateByShopId(req.params.shopid, req.body)
    .then(shop => res.send(shop))
    .catch(err => res.status(500).send(err));
});

// Delete by id
router.delete('/shopid/:shopid', (req, res) => {
  Shop.deleteByShopId(req.params.shopid)
    .then(() => res.sendStatus(200))
    .catch(err => res.status(500).send(err));
});

module.exports = router;
