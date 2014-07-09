require('./config/mongoose');
require('newrelic');

var crawler = require('./crawler/crawler');
var Complain = require('./models').Complain;
var Task = require('./models').Task;
var async = require('async');

var updateItemPerComplainDelay = 8000;

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
                console.log('[crawler.crawlWithDate] item_list:', item_list);
                var functions = [];
                item_list.forEach(function(id) {
                    if (!id || id.length == 0 || id === '') return;
                    functions.push(function(callback) {
                        Complain.findById(id, function(error, complain) {
                            console.log('[Complain.findById]', id, error, complain);
                            if (error || complain) {
                                callback(error, complain);
                            }
                            else {
                                // if no error and no existed complain object then create new one.
                                Complain.create({
                                    cid: id,
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

var updateItemsLock = false;
var updateItemsDelay = 1000 * 5;
function updateItems() {
    console.log('[updateItems] updateItemsLock=', updateItemsLock);
    if (updateItemsLock) {
        return ;
    }
    updateItemsLock = true;
    //Complain.where('status').ne('done').exec(function(err, complains) {
    Complain.where('status').equals('new').exec(function(err, complains) {
        var complainIndex = 0;
        console.log('[updateItems]', 'complain length=', complains.length);
        var loop = setInterval(function() {
            console.log('[updateItems.complainIndex]', complainIndex);
            if (complainIndex == complains.length) {
                updateItemsLock = false;
                clearInterval(loop);
                return;
            }
            console.log('[updateItems] updating complain:', complains[complainIndex]);
            var cid = complains[complainIndex].cid;
            crawler.updateItem(cid, function(jsondata) {
                console.log('[updateItems.crawler.updateItem]', 'cid=', cid, 'jsondata=', jsondata);
                Complain.findByIdAndUpdate(
                    cid, 
                    jsondata, 
                    { upsert: true},
                    function(error, complain) {
                        console.log('[updateItems.Complain.findByIdAndUpdate]', error, complain);
                    }
                );
            });
            ++complainIndex;
        }, updateItemPerComplainDelay);
    });

}

buildList();
setInterval(buildList, taskDelay);

updateItems();
setInterval(updateItems, updateItemsDelay);
