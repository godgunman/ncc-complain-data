require('./config/mongoose');
require('newrelic');

var crawler = require('./crawler/crawler');
var Complain = require('./models').Complain;
var Task = require('./models').Task;
var async = require('async');

var single_delay = 8000;
var delay = 1000 * 60 * 10;

var taskDelay = 1000 * 5;
var taskLock = false;
//var time_diff  = 1000 * 60 * 5;

function buildList () {

    console.log('[task]', 'task lock = ', taskLock);
    if (taskLock) return ;
    taskLock = true;
    Task.findOne({status:'pending'}, function(err, task) {
        if (task) {
            var date = new Date(task.year, task.month - 1, task.day);
            console.log('[task]', task, 'date =', date);
            crawler.crawlWithDate(date, function(item_list) {
                console.log('item_list', item_list);
                var functions = [];
                item_list.forEach(function(id) {
                    functions.push(function(callback) {
                        Complain.findById(id, function(error, complain) {
                            console.log('[Complain.findById]', id, error, complain);
                            if (error || complain) {
                                callback(error, complain);
                            }
                            else {
                                // if no error and no existed complain object then create new one.
                                Complain.create({
                                    cid: item_list[i],
                                    status: 'new',
                                }, function(error, complain) {
                                    console.log('[Complain.create]', error, complain);
                                    callback(error, complain);
                                });
                            }
                        });
                    });
                });
                async.parallel(functions, function(error, results) {
                    console.log('[async.parallel]', error, results);
                    task.status = 'done';
                    task.save(function(error, task) {
                        taskLock = false;
                    });
                });
            });
            task.status = 'processing';
            task.save();
        } else {
            console.log('[task]', 'no task.');
            taskLock = false;
        }
    });
}

var complain_idx = 0;

function updateItems() {
    //Complain.where('status').ne('done').exec(function(err, complains) {
    Complain.where('status').equals('new').exec(function(err, complains) {
        console.log(complains);
        var loop = setInterval(function() {
            console.log('complain_idx', complain_idx);
            if (complain_idx == complains.length) {
                clearInterval(loop);
                return;
            }
            console.log('spirit breaker ' + complains[complain_idx]);
            var cid = complains[complain_idx].cid;
            crawler.updateItem(cid, function(jsondata) {
                console.log('cid=', cid, 'jsondata=', jsondata);
                Complain.findByIdAndUpdate(
                    cid, 
                    jsondata, 
                    { upsert: true},
                    function(err, complain) {
                        if (err) {
                            console.log('error:', err);
                        } else {
                            console.log('saved: id=', complain.id);
                        }
                    }
                );
            });
            ++complain_idx;
        }, single_delay);
    });

}

buildList();
setInterval(buildList, taskDelay);

updateItems();
setInterval(updateItems, delay * 2);
