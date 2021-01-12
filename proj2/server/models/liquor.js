const mongoose = require('mongoose');

// Define Schemes
const liquorSchema = new mongoose.Schema({
	_id:{type:Number},
	name:{type:String},
	type:{type:String},
	price:{type:Number},
	degree:{type:Number},
	detail:{type:String},
	imgUri:{type:String}
},
{
	timestamps:false,
	versionKey:false
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

// Find One by Name
liquorSchema.statics.findOneByName = function (name) {
  return this.find({ name: name });
};

// Find One by Id
liquorSchema.statics.findOneById = function (_id) {
  return this.find({ _id: _id });
};

// Update by Id
liquorSchema.statics.updateById = function (_id, payload) {
  // { new: true }: return the modified document rather than the original. defaults to false
  return this.findOneAndUpdate({ _id: _id }, payload, { new: true });
};

// Delete by Id
liquorSchema.statics.deleteById = function (_id) {
  return this.remove({ _id: _id });
};

// Create Model & Export
module.exports = mongoose.model('Liquor', liquorSchema);
