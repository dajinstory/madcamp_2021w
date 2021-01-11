const mongoose = require('mongoose');

// Define Schemes
const shopSchema = new mongoose.Schema({
	_id:{type:Number, required:true, unique:true},
	name:{type:String},
	type:{type:String},
	contact:{type:String},
	address:{type:String},
	businessHour:{type:String},
	detail:{type:String},
	imgUri:{type:String}
},
{
	timestamps:true
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

// Find One by shopid
shopSchema.statics.findOneByShopId = function (id) {
  return this.findOne({ id });
};

// Update by shopAccountId
shopSchema.statics.updateByShopId = function (id, payload) {
  // { new: true }: return the modified document rather than the original. defaults to false
  return this.findOneAndUpdate({ id }, payload, { new: true });
};

// Delete by shopAccountId
shopSchema.statics.deleteByShopId = function (id) {
  return this.remove({ id });
};

// Create Model & Export
module.exports = mongoose.model('Shop', shopSchema);
