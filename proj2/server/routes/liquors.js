const router = require('express').Router();
const Liquor = require('../models/liquor');

// Find All
router.get('/', (req, res) => {
  Liquor.findAll()
    .then((liquors) => {
      if (!liquors.length) return res.status(404).send({ err: 'Liquor not found' });
      res.send(`find successfully: ${liquors}`);
    })
    .catch(err => res.status(500).send(err));
});

// Find One by id
router.get('/liquorid/:liquorid', (req, res) => {
  Liquor.findOneByLiquorId(req.params.liquorid)
    .then((liquor) => {
      if (!liquor) return res.status(404).send({ err: 'Liquor not found' });
      res.send(`findOne successfully: ${liquor}`);
    })
    .catch(err => res.status(500).send(err));
});

// Create new liquor document
router.post('/', (req, res) => {
  Liquor.create(req.body)
    .then(liquor => res.send(liquor))
    .catch(err => res.status(500).send(err));
});

// Update by id
router.put('/liquorid/:liquorid', (req, res) => {
  Liquor.updateByLiquorId(req.params.liquorid, req.body)
    .then(liquor => res.send(liquor))
    .catch(err => res.status(500).send(err));
});

// Delete by id
router.delete('/liquorid/:liquorid', (req, res) => {
  Liquor.deleteByLiquorId(req.params.liquorid)
    .then(() => res.sendStatus(200))
    .catch(err => res.status(500).send(err));
});

module.exports = router;
