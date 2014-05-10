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
var crawler = require('../../crawler/crawler');
var _ = require('underscore');
var Q = require('q');

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

    crawl: function(req, res) {
        var results = [];
        var promises = [];

        promises.push(function() {
            var outterDeferred = Q.defer();
            crawler.crawlWithDate(req.query.date, function(html) {
                parseHtml.parseByText(html, function(e){
                    promises.push(function(){
                        results.push(_.clone(e));
                        var deferred = Q.defer();
                        var wrapElement = {};
                        for (key in e) {
                            if (key == 'date')
                                wrapElement[key] = new Date(e[key].value);
                            else 
                                wrapElement[key] = e[key].value;
                        }
                        Complain.create(wrapElement).done(function(err, complain) {
                            console.log(err, complain);
                            results.push(complain);
                            deferred.resolve(e);
                        });

                        return deferred.promise;
                    }());
                    outterDeferred.resolve(e);
                });
            });
            return outterDeferred.promise;
        }());
        Q.all(promises).then(function() {
            res.json(results);
        });
    },
    /**
     * Overrides for the settings in `config/controllers.js`
     * (specific to ComplainController)
     */
    _config: {}
};
