require('./config/mongoose');
require('newrelic');

var crawler = require('./crawler/crawler');
var Complain = require('./models').Complain;
var Task = require('./models').Task;

var delay = 1000 * 60 * 10;

function go () {
    var date = new Date(2014, 5 - 1, 23);
    //console.log('jizz ' + date.toString());
    crawler.crawlWithDate(date, function(item_list) {
        for (var i = 0; i < item_list.length - 1; ++i) {
            var complain = new Complain({
                cid: item_list[i],
                status: 'new'
            });
            console.log(complain);
        }
        //complain.se
        complain.save(function(err, complain) {
            if (err) console.error(err);
            if (complain) {
                console.log('[complain]', complain.cid, 'saved.');
           //     task.status = 'done';
           //     task.save();
            }
        });
    //    console.log(json);
    });
    Task.findOne({status:'pending'}, function(err, task) {
        if (task) {
            //var date = task.year + '/' + task.month + '/' + task.day;
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
    });
}

go();
setInterval(go, delay);
