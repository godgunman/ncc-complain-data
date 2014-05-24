/**
 * ComplainController
 *
 * @module      :: Controller
 * @description	:: A set of functions called `actions`.
 *
 *                 Actions contain code telling Sails how to respond to a certain type of request.
 *                 (i.e. do stuff, then send some JSON, show an HTML page, or redirect to another URL)
 *
 *                 You can configure the blueprint URLs which trigger these actions (`config/controllers.js`)
 *                 and/or override them with custom routes (`config/routes.js`)
 *
 *                 NOTE: The code you write here supports both HTTP and Socket.io automatically.
 *
 * @docs        :: http://sailsjs.org/#!documentation/controllers
 */

var parseHtml = require('../utils/parseHtml');
var _ = require('underscore');
var Q = require('q');
var mongoose = require("mongoose");
var Complain = mongoose.model('Complain');

module.exports = {

    test: function(req, res) {
        parseHtml.test(function(results) {
            var resultsClone = [];
            results.forEach(function(e) {
                resultsClone.push(_.clone(e));
                var wrapElement = {};
                for (key in e) {
                    if (key == 'date')
                        wrapElement[key] = new Date(e[key].value);
                    else 
                        wrapElement[key] = e[key].value;
                }
                Complain.create(wrapElement).done(function(err, complain) {
                    console.log(err, complain);
                });
            });
            //            console.log(results);

            res.json({
                data:resultsClone
            });
        });
    }, 

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

    /**
     * Overrides for the settings in `config/controllers.js`
     * (specific to ComplainController)
     */
    _config: {}
};
