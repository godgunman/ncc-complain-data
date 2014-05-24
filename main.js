require('./config/mongoose');
require('newrelic');

var crawler = require('./crawler/crawler');
var Complain = require('./models').Complain;
var Task = require('./models').Task;

var single_delay = 8000;
var delay = 1000 * 60 * 10;
//var time_diff  = 1000 * 60 * 5;

function buildList () {

    Task.findOne({status:'pending'}, function(err, task) {
        if (task) {
            var date = new Date(task.year, task.month - 1, task.day);
            console.log('[task]', task, 'date =', date);
            crawler.crawlWithDate(date, function(item_list) {
                for (var i = 0; i < item_list.length - 1; ++i) {
                    Complain.findByIdAndUpdate(item_list[i], {
                        cid: item_list[i],
                        status: 'new'
                        },
                        { upsert: true },
                        function(err, complain) {
                            console.log(err);
                            console.log(complain);
                        }
                    );
                }
                task.status = 'done';
            });
            task.status = 'processing';
            task.save();
        } else {
            console.log('[task]', 'no task.');
        }
    });
}

var complain_idx = 0;

function updateItems() {
    //Complain.where('status').ne('done').exec(function(err, complains) {
    Complain.where('status').equals('new').exec(function(err, complains) {
	    console.log(complains);
        var loop = setInterval(function() {
			console.log('jizz' + complain_idx);
			if (complain_idx == complains.length) {
				clearInterval(loop);
				return;
			}
			console.log('spirit breaker ' + complains[complain_idx]);
			crawler.updateItem(complains[complain_idx].cid, function(jsondata) {
				Complain.findByIdAndUpdate(complains[complain_idx].cid, jsondata, { upsert: true},
					function(err, complain) {
						console.log(err, complain);
					}
				)
			});
			++complain_idx;
        }, single_delay);
    });
    
}

buildList();
setInterval(buildList, delay);
updateItems();
setInterval(updateItems, delay * 2);
