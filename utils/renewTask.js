require('./config/mongoose');
var mongoose = require('mongoose');
var Task = require('./models').Task;

setTimeout(function(){
    Task.find({}, function(error, tasks) {
        tasks.forEach(function(task){
            console.log(task);
            task.status = 'pending';
            task.save();
        });
    });
}, 5000);
