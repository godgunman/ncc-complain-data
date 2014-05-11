var env = {};
try { 
    env = require('./local');
} catch (e) {
    env = process.env;
}

var MONGOHQ_URL = "mongodb://" + env.USER + ":" + env.PASSWORD + "@oceanic.mongohq.com:10094/ncc-complain";
var mongoose = require("mongoose");

var db = mongoose.connection;
db.on('error', console.error.bind(console, '[mongodb] connection error:'));
db.once('open', function callback () {
    console.log('[mongodb] connection OK.');
});

module.exports = {
    mongoose: mongoose.connect(MONGOHQ_URL),
}
