var MONGOHQ_URL="mongodb://<user>:<password>@oceanic.mongohq.com:10094/ncc-complain"
var mongoose = require("mongoose");

module.exports = {
    mongoose: mongoose.connect(MONGOHQ_URL),
}
