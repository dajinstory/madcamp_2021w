const router = require('express').Router();
const FBA = require('../models/fba');

// Find All
router.get('/', (req, res) => {
  FBA.findAll()
    .then((fbas) => {
      if (!fbas.length) return res.status(404).send({ err: 'FBA not found' });
      res.send(`find successfully: ${fbas}`);
    })
    .catch(err => res.status(500).send(err));
});

// Find One by id
router.get('/fbaid/:fbaid', (req, res) => {
  FBA.findOneByFBAId(req.params.fbaid)
    .then((fba) => {
      if (!fba) return res.status(404).send({ err: 'FBA not found' });
      res.send(`findOne successfully: ${fba}`);
    })
    .catch(err => res.status(500).send(err));
});

// Create new fba document
router.post('/', (req, res) => {
  FBA.create(req.body)
    .then(fba => res.send(fba))
    .catch(err => res.status(500).send(err));
});

// Update by id
router.put('/fbaid/:fbaid', (req, res) => {
  FBA.updateByFBAId(req.params.fbaid, req.body)
    .then(fba => res.send(fba))
    .catch(err => res.status(500).send(err));
});

// Delete by id
router.delete('/fbaid/:fbaid', (req, res) => {
  FBA.deleteByFBAId(req.params.fbaid)
    .then(() => res.sendStatus(200))
    .catch(err => res.status(500).send(err));
});

module.exports = router;
