var path = require('path')
var childProcess = require('child_process')
var phantomjs = require('phantomjs')
var parseHtml = require('./parseHtml');
var binPath = phantomjs.path

function fillZero(value, digit) {
    var valueStr = value.toString();
    var cnt = valueStr.length;
    var str = '';
    while (cnt < digit) {
        str += '0';
        ++cnt;
    }
    str += valueStr;
    return str;
}

function getFormattedDate(year, month, date) {
    var yearStr = year.toString();
    var monthStr = fillZero(month, 2);
    var dayStr = fillZero(date, 2);
    return yearStr + '/' + monthStr + '/' + dayStr;
}

function getItemListWithDate(date, callback) {
    console.log('prepare get item count');
    // prepare
    var dateStr = getFormattedDate(date.getFullYear(),
                                   date.getMonth() + 1,
                                   date.getDate());
    var childArgs = [
        '--ignore-ssl-errors=true',
        path.join(__dirname, 'get_item_list_with_date.js'), dateStr
    ]

    // run
    childProcess.execFile(binPath, childArgs, function(err, stdout, stderr) {
        console.log('get item list done');
        
        var itemList = stdout.split('\n');
        callback(itemList);
    })
}

//function getSerial

function getPageById(id, callback) {
    // prepare
    console.log('get page by date & id');
    var childArgs = [
        '--ignore-ssl-errors=true',
        path.join(__dirname, 'get_page_by_id.js'),
        id
    ]
    console.log(path.join(__dirname, 'get_page_by_id.js'));
    // run
    childProcess.execFile(binPath, childArgs, function(err, stdout, stderr) {
        console.log('exec get page done');
        if (callback) {
            console.log('try callback');
            var jsondata = parseHtml.parseByText(stdout);
	    console.log(jsondata);
	    callback(jsondata);
        }
    })
}

function crawlWithDate(date, callback) {
    getItemListWithDate(date, callback);
}


function updateItem(cid, callback) {
    getPageById(cid, callback);
}

module.exports = {
    'crawlWithDate': crawlWithDate,
    'updateItem': updateItem
}
