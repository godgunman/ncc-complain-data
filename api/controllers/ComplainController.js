var mongoose = require("mongoose");
var Complain = mongoose.model('Complain');

module.exports = {
    find: function(req, res) {
        var id = req.params.id;
        if (id) {
            Complain.findById(id, function(error, complain) {
                res.json({
                    error: error,
                    reseult: complain,
                });
            });
        } else {
            Complain.find(function(error, complains) {
                res.json({
                    error: error,
                    result: complains,
                });
            });
        }
    },
};
