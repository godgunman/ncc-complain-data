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
            var query = req.query;
            var limit = 100, skip = 0;

            if (query.limit) {
                limit = query.limit;
                delete query.limit;
            }
            if (query.skip) {
                skip = query.skip;
                delete query.skip;
            }

            console.log(query);

            Complain
            .find(query)
            .sort({cid: 1})
            .limit(limit)
            .skip(skip)
            .exec(function(error, complains) {
                res.json({
                    error: error,
                    result: complains,
                });
            });
        }
    },
};
