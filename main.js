require('./config/mongoose');
require('newrelic');

var crawler = require('./crawler/crawler');
var Complain = require('./models').Complain;
var Task = require('./models').Task;

var delay = 1000 * 60 * 10;

function go () {
    var date = '2014/05/24';
    var year = 2014
    var month = 5;
    var day = 24;
    crawler.crawlWithDate(date,function(json) {
        //var complain = new Complain(json);
        /*complain.save(function(err, complain) {
            if (err) console.error(err);
            if (complain) {
                console.log('[complain]', complain.cid, 'saved.');
                task.status = 'done';
                task.save();
            }
        });*/
        console.log(json);
    });
    /*Task.findOne({status:'pending'}, function(err, task) {
        if (task) {
            var date = task.year + '/' + task.month + '/' + task.day;
            console.log('[task]', task, 'date =', date);
            crawler.crawlWithDate(date, function(json) {
                var complain = new Complain(json);
                complain.save(function(err, complain) {
                    if (err) console.error(err);
                    if (complain) {
                        console.log('[complain]', complain.cid, 'saved.');
                        task.status = 'done';
                        task.save();
                    }
                });
            });
            task.status = 'processing';
            task.save();
        } else {
            console.log('[task]', 'no task.');
        }
    });*/
}

go();
setInterval(go, delay);
