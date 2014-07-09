require('../config/mongoose');
var mongoose = require('mongoose');
var Task = require('../models').Task;

var current = new Date();
for (var y = 2010 ;y <= 2014; y++) {
    for ( var m = 1; m <= 12; m++) {
        for ( var d = 1; d <= 31; d++) {
            var date = Date.parse([y, m, d].join('-'));
            if (date && date < current) {
                console.log(y - 1911, m, d)

                var conditions = {
                    year: y - 1911,
                    month: m,
                    day: d,
                };

                var update = {
                    year: y - 1911,
                    month: m,
                    day: d,
                    status: 'pending',
                };

                Task.findOneAndUpdate(conditions, update, {upsert:true}, function(error, task){
                    console.log('[genTask]', error, task);
                });
            }
        }
    }
}
