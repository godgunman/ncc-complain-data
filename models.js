var mongoose = require("mongoose");
var complainSchema = mongoose.Schema({
    _id: String,
    cid: String,
    date: Date,
    channelCategory: String,
    channelName: String,
    programName: String,
    broadcastDate: String,
    broadcastTime: String,
    contentCategory: String,
    complainCategory: String,
    complainTitle: String,
    complainContent: String,
});

complainSchema.path('cid').set(function(v) {
    this._id = v;
    return v;
});

complainSchema.pre('save', function(next) {
    this._id = this.cid;
    next();
});

module.exports = {
    Complain: mongoose.model('Complain', complainSchema),
};
