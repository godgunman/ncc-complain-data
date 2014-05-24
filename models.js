var mongoose = require("mongoose");
var complainSchema = mongoose.Schema({
    _id: String,
    cid: String,
    date: Date,
    status: String,
    channelCategory: String,
    channelName: String,
    programName: String,
    broadcastDate: String,
    broadcastTime: String,
    contentCategory: String,
    complainCategory: String,
    complainTitle: String,
    complainContent: String,
	replyContent: String,
});

complainSchema.path('cid').set(function(v) {
    this._id = v;
    return v;
});

complainSchema.pre('save', function(next) {
    this._id = this.cid;
    next();
});

var taskSchema = mongoose.Schema({
    year: Number,
    month: Number,
    day: Number,
    status: String,
});


module.exports = {
    Complain: mongoose.model('Complain', complainSchema),
    Task: mongoose.model('Task', taskSchema),
};
