var MONGOHQ_URL = "mongodb://test2:test2@oceanic.mongohq.com:10094/ncc-complain";
var mongoose = require("mongoose");

var db = mongoose.connection;
db.on('error', console.error.bind(console, '[mongodb] connection error:'));
db.once('open', function callback () {
    console.log('[mongodb] connection OK.');
});

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

mongoose.model('Complain', complainSchema);

module.exports = {
    mongoose: mongoose.connect(MONGOHQ_URL),
}
