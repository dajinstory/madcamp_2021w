const mongoose = require('mongoose');

// Define Schemes
const imageSchema = new mongoose.Schema({
	id:{type:String, required:true, unique:true},
	image:{type:String}
},{
	timestamps:true
},{
	collection : "Image"	
});

// Create new document
fbaSchema.statics.create = function(payload) {
	const fba = new this(payload);
	return fba.save();
}

// Find All
fbaSchema.statics.findAll = function () {
  return this.find({});
};

// Find One by fbaid
fbaSchema.statics.findOneByFBAId = function (id) {
  return this.findOne({ id });
};

// Update by facebookAccountId
fbaSchema.statics.updateByFBAId = function (id, payload) {
  // { new: true }: return the modified document rather than the original. defaults to false
  return this.findOneAndUpdate({ id }, payload, { new: true });
};

// Delete by facebookAccountId
fbaSchema.statics.deleteByFBAId = function (id) {
  return this.remove({ id });
};

// Create Model & Export
module.exports = mongoose.model('FBA', fbaSchema);
