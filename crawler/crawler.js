var path = require('path')
var childProcess = require('child_process')
var phantomjs = require('phantomjs')
var parseHtml = require('./parseHtml');
var binPath = phantomjs.path

function getItemCountWithDate(date, callback) {
    console.log('prepare get item count');
    // prepare
    var childArgs = [
        '--ignore-ssl-errors=true',
        path.join(__dirname, 'get_item_count_with_date.js'), date
    ]

    // run
    childProcess.execFile(binPath, childArgs, function(err, stdout, stderr) {
        item_count = parseInt(stdout);
        console.log('exec get item count done, count: ' + item_count);
        // loop
        if (item_count < 50) {
            for (var i = 0; i < item_count; i++) {
                getPageWithDateItem(date, i, callback);
            }
        }
    })
}

function getPageWithDateItem(date, item, callback) {
    console.log('get page with date item');
    // prepare
    var childArgs = [
        '--ignore-ssl-errors=true',
        path.join(__dirname, 'get_page_with_date_item.js'), date, item
    ]

    // run
    childProcess.execFile(binPath, childArgs, function(err, stdout, stderr) {
        console.log('exec get page done');
        if (callback) {
            console.log('try callback');
            callback(stdout);
        }
    })
}

function crawlWithDate(date, callback) {
    getItemCountWithDate(date, function(html) {
        var jsonData = parseHtml.parseByText(html);
        if (callback) {
            callback(jsonData);
        }
    });
}

module.exports = {
    'crawlWithDate': crawlWithDate
}
