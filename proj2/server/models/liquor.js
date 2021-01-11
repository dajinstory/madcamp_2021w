const mongoose = require('mongoose');

// Define Schemes
const liquorSchema = new mongoose.Schema({
	_id:{type:Number, required:true, unique:true},
	name:{type:String},
	type:{type:String},
	price:{type:Number},
	degree:{type:Number},
	detail:{type:String},
	imgUri:{type:String}
},
{
	timestamps:true
});

// Create new document
liquorSchema.statics.create = function(payload) {
	const liquor = new this(payload);
	return liquor.save();
}

// Find All
liquorSchema.statics.findAll = function () {
  return this.find({});
};

// Find One by liquorid
liquorSchema.statics.findOneByLiquorId = function (id) {
  return this.findOne({ id });
};

// Update by liquorAccountId
liquorSchema.statics.updateByLiquorId = function (id, payload) {
  // { new: true }: return the modified document rather than the original. defaults to false
  return this.findOneAndUpdate({ id }, payload, { new: true });
};

// Delete by liquorAccountId
liquorSchema.statics.deleteByLiquorId = function (id) {
  return this.remove({ id });
};

// Create Model & Export
module.exports = mongoose.model('Liquor', liquorSchema);
