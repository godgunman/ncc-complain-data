var _ = require('underscore');
var mongoose = require("mongoose");
var Complain = mongoose.model('Complain');

module.exports = {
    testData: function(req, res) {
        var request = require('request');
        request('http://fakenews.tw/api/channel', function (error, response, body) {
            if (!error && response.statusCode == 200) {
                res.json(JSON.parse(body));
            }
        });
    },

    find: function(req, res) {
        var channels = {};
        Complain.find({
            status: {
                $ne: 'new',
            },
            channelName: {
                $ne: '',
            }
        }, 'channelName complainCategory', function(error, results) {
            if (error) {
                res.json({error: error});
                return;
            }
            results.forEach(function(e) {
                if (!channels[e.channelName]) {
                    channels[e.channelName] = {
                        size:0,
                    };
                }
                if (!channels[e.channelName][e.complainCategory]) {
                    channels[e.channelName][e.complainCategory] = [];
                }
                channels[e.channelName][e.complainCategory].push(e);
                channels[e.channelName].size ++;
            });
            channelArray = [];
            for (key in channels) {
                var category = [];
                for (var categoryKey in channels[key]) {
                    if (categoryKey=='size') continue;
                    category.push({
                        categoryName: categoryKey,
                        size: channels[key][categoryKey].length,
                        //                        data: channels[key][categoryKey],
                    });
                }
                category = _.sortBy(category, function(e) {
                    return -e.size;
                });
                channelArray.push({
                    channelName: key,
                    category: category,
                    size: channels[key].size,
                });
            }
            channels = _.sortBy(channelArray, function(channel) {return -channel.size});
            res.json({result: channels});
        });
    },
};
