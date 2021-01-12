const mongoose = require('mongoose');

// Define Schemes
const shopSchema = new mongoose.Schema({
	_id:{type:Number},
	name:{type:String},
	type:{type:String},
	contact:{type:String},
	address:{type:String},
	businessHour:{type:String},
	detail:{type:String},
	imgUri:{type:String}
},
{
	timestamps:false,
	versionKey:false
	
});

// Create new document
shopSchema.statics.create = function(payload) {
	const shop = new this(payload);
	return shop.save();
}

// Find All
shopSchema.statics.findAll = function () {
  return this.find({});
};

// Find One by Name
shopSchema.statics.findOneByName = function (name) {
  return this.findOne({ name: name });
};

// Find One by Id
shopSchema.statics.findOneById = function (_id) {
  return this.findOne({ _id: _id });
};

// Update by Id
shopSchema.statics.updateById = function (_id, payload) {
  // { new: true }: return the modified document rather than the original. defaults to false
  return this.findOneAndUpdate({ _id: _id }, payload, { new: true });
};

// Delete by Id
shopSchema.statics.deleteById = function (_id) {
  return this.remove({ _id: _id });
};

// Create Model & Export
module.exports = mongoose.model('Shop', shopSchema);
