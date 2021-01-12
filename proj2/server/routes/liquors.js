const router = require('express').Router();
const Liquor = require('../models/liquor');

// Find All
router.get('/', (req, res) => {
  console.log("GET Liquor ALL");
  Liquor.findAll()
    .then((liquors) => {
	  console.log("TRY");
      if (!liquors.length) return res.status(404).send({ err: 'Liquor not found' });
      console.log("SUCCESS");
	  //res.send(`find successfully: ${liquors}`);
	  res.json(liquors);
    })
    .catch(err => res.status(500).send(err));
});

// Find One by name
router.get('/name/:name/', (req, res) => {
  console.log("GET Liquor Single");
  Liquor.findOneByName(req.params.name)
    .then((liquor) => {
      console.log("TRY");
      if (!liquor) return res.status(404).send({ err: 'Liquor not found' });
	  console.log("SUCCESS");
	  //res.send(`findOne successfully: ${liquor}`);
      res.json(liquor);
    })
    .catch(err => res.status(500).send(err));
});

// Find One by id
router.get('/id/:_id/', (req, res) => {
  console.log("GET Liquor Single");
  Liquor.findOneById(req.params._id)
    .then((liquor) => {
      console.log("TRY");
      if (!liquor) return res.status(404).send({ err: 'Liquor not found' });
	  console.log("SUCCESS");
	  //res.send(`findOne successfully: ${liquor}`);
      res.json(liquor);
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
router.put('/id/:_id/', (req, res) => {
  Liquor.updateById(req.params._id, req.body)
    .then(liquor => res.send(liquor))
    .catch(err => res.status(500).send(err));
});

// Delete by id
router.delete('/id/:_id/', (req, res) => {
  Liquor.deleteById(req.params._id)
    .then(() => res.sendStatus(200))
    .catch(err => res.status(500).send(err));
});

module.exports = router;
