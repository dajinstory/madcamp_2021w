// Env
require('dotenv').config();

// Dependencies
const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const methodOverride = require('method-override');

const app = express();
const port = process.env.PORT || 8080;

// Static File Service
app.use(express.static('public'));

// Body-parser
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));

// Set nodejs.promise as mongoose.promise 
mongoose.Promise = global.Promise;

// DB setting
mongoose.set('useNewUrlParser', true);
mongoose.set('useFindAndModify', false);
mongoose.set('useCreateIndex', true);
mongoose.set('useUnifiedTopology', true);

// Connect to MongoDB Server
mongoose.connect(process.env.MONGO_URI)
	.then(() => console.log('Successfully connected to mongodb'))
	.catch(e => console.error(e));

// Routers
app.use('/fbas', require('./routes/fbas'));


app.listen(port, () => console.log(`Server listening on port ${port}`));

