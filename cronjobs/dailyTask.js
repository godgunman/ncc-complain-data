require('../config/mongoose');
var mongoose = require('mongoose');
var Task = require('../models').Task;

var defaultDelay = 1000 * 60 * 60 * 24;
var newDay = function () {
    var d = new Date();
    var year = d.getFullYear() - 1911;
    var month = d.getMonth() + 1;
    var date = d.getDate();

    var conditions = {
        year: year,
        month: month,
        day: date,
    };

    var update = {
        year: year,
        month: month,
        day: date,
        status: 'pending',
    };

    Task.findOneAndUpdate(conditions, update, {upsert:true}, function(error, task){
        console.log('[NewTask]', error, task);
    });
};

module.exports = {
    run: function(delay) {
        newDay();
        setInterval(newDay, delay || defaultDelay);
    }
}
