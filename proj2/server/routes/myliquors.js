const router = require('express').Router();
const MyLiquor = require('../models/myliquor');

// Find All
router.get('/', (req, res) => {
  console.log("GET MyLiquor ALL");
  MyLiquor.findAll()
    .then((myliquors) => {
	  console.log("TRY");
      if (!myliquors.length) return res.status(404).send({ err: 'MyLiquor not found' });
      console.log("SUCCESS");
	  //res.send(`find successfully: ${myliquors}`);
	  res.json(myliquors);
    })
    .catch(err => res.status(500).send(err));
});

// Find One by name
router.get('/name/:name/', (req, res) => {
  console.log("GET MyLiquor Single");
  MyLiquor.findOneByName(req.params.name)
    .then((myliquor) => {
      console.log("TRY");
      if (!myliquor) return res.status(404).send({ err: 'MyLiquor not found' });
	  console.log("SUCCESS");
	  //res.send(`findOne successfully: ${myliquor}`);
      res.json(myliquor);
    })
    .catch(err => res.status(500).send(err));
});

// Find One by id
router.get('/id/:_id/', (req, res) => {
  console.log("GET MyLiquor Single");
  MyLiquor.findOneById(req.params._id)
    .then((myliquor) => {
      console.log("TRY");
      if (!myliquor) return res.status(404).send({ err: 'MyLiquor not found' });
	  console.log("SUCCESS");
	  //res.send(`findOne successfully: ${myliquor}`);
      res.json(myliquor);
    })
    .catch(err => res.status(500).send(err));
});

// Create new myliquor document
router.post('/', (req, res) => {
  MyLiquor.create(req.body)
    .then(myliquor => res.send(myliquor))
    .catch(err => res.status(500).send(err));
});

// Update by id
router.put('/id/:_id/', (req, res) => {
  MyLiquor.updateById(req.params._id, req.body)
    .then(myliquor => res.send(myliquor))
    .catch(err => res.status(500).send(err));
});

// Delete by id
router.delete('/id/:_id/', (req, res) => {
  MyLiquor.deleteById(req.params._id)
    .then(() => res.sendStatus(200))
    .catch(err => res.status(500).send(err));
});

module.exports = router;
