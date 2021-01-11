const router = require('express').Router();
const Shop = require('../models/liquor');

// Find All
router.get('/', (req, res) => {
  Shop.findAll()
    .then((liquors) => {
      if (!liquors.length) return res.status(404).send({ err: 'Shop not found' });
      res.send(`find successfully: ${liquors}`);
    })
    .catch(err => res.status(500).send(err));
});

// Find One by id
router.get('/liquorid/:liquorid', (req, res) => {
  Shop.findOneByShopId(req.params.liquorid)
    .then((liquor) => {
      if (!liquor) return res.status(404).send({ err: 'Shop not found' });
      res.send(`findOne successfully: ${liquor}`);
    })
    .catch(err => res.status(500).send(err));
});

// Create new liquor document
router.post('/', (req, res) => {
  Shop.create(req.body)
    .then(liquor => res.send(liquor))
    .catch(err => res.status(500).send(err));
});

// Update by id
router.put('/liquorid/:liquorid', (req, res) => {
  Shop.updateByShopId(req.params.liquorid, req.body)
    .then(liquor => res.send(liquor))
    .catch(err => res.status(500).send(err));
});

// Delete by id
router.delete('/liquorid/:liquorid', (req, res) => {
  Shop.deleteByShopId(req.params.liquorid)
    .then(() => res.sendStatus(200))
    .catch(err => res.status(500).send(err));
});

module.exports = router;
