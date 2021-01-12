const mongoose = require('mongoose');

// Define Schemes
const myliquorSchema = new mongoose.Schema({
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
myliquorSchema.statics.create = function(payload) {
	const myliquor = new this(payload);
	return myliquor.save();
}

// Find All
myliquorSchema.statics.findAll = function () {
  return this.find({});
};

// Find One by Name
myliquorSchema.statics.findOneByName = function (name) {
  return this.find({ name: name });
};

// Find One by Id
myliquorSchema.statics.findOneById = function (_id) {
  return this.find({ _id: _id });
};

// Update by Id
myliquorSchema.statics.updateById = function (_id, payload) {
  // { new: true }: return the modified document rather than the original. defaults to false
  return this.findOneAndUpdate({ _id: _id }, payload, { new: true });
};

// Delete by Id
myliquorSchema.statics.deleteById = function (_id) {
  return this.remove({ _id: _id });
};

// Create Model & Export
module.exports = mongoose.model('MyLiquor', myliquorSchema);
