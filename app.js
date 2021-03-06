
/**
 * Module dependencies.
 */

var express = require('express');
var routes = require('./routes');
var user = require('./routes/user');
var http = require('http');
var path = require('path');
var mongoose = require('./config/mongoose');

var ComplainController = require('./api/controllers/ComplainController');
var ChannelController = require('./api/controllers/ChannelController');
var HomeController = require('./api/controllers/HomeController');
var TableController = require('./api/controllers/TableController');
var ChartController = require('./api/controllers/ChartController');

var app = express();

// all environments
app.set('port', process.env.PORT || 3000);
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(express.favicon(__dirname + '/public/images/favicon.ico')); 
app.use(express.logger('dev'));
app.use(express.json());
app.use(express.urlencoded());
app.use(express.methodOverride());
app.use(app.router);
app.use(require('stylus').middleware(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'public')));

// development only
if ('development' == app.get('env')) {
    app.use(express.errorHandler());
}

app.all('/', function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    next();
});

app.get('/', HomeController.index);

app.get('/api/complain/count', ComplainController.count);
app.get('/api/complain/:id?', ComplainController.find);
app.get('/api/channel', ChannelController.find);
app.get('/api/channel/testData', ChannelController.testData);

app.get('/table', TableController.index);
app.get('/table/detail', TableController.detail);

app.get('/chart/basic', ChartController.basic);

http.createServer(app).listen(app.get('port'), function(){
    console.log('Express server listening on port ' + app.get('port'));
});
