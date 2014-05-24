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
    //var serialStr = yearStr + monthStr + dayStr + 'T' + fillZero(id, 5);
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
        //for (var i = 0; i < itemList.length; ++i)
        //    console.log(itemList[i]);
        //item_count = parseInt(stdout);
        //console.log('exec get item count done, count: ' + item_count);
        //for (var i = 0; i < item_count; i++) {
        //    callback();
        //}
        // loop
        /*if (item_count < 50) {
            for (var i = 0; i < item_count; i++) {
                getPageWithDateItem(date, i, callback);
            }
        }*/
    })
}

//function getSerial

function getPageByDateAndId(date, id, callback) {
    // prepare
    console.log('get page by date & id');
    var childArgs = [
        '--ignore-ssl-errors=true',
        path.join(__dirname, 'get_page_by_date_and_id.js'),
        date.getFullYear(),
        date.getMonth() + 1,
        date.getDate(),
        id
    ]
    console.log(path.join(__dirname, 'get_page_by_date_and_id.js'));
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
    getItemListWithDate(date, callback);
    /*var id = 1;
    while (true) {
        //var serial = getFormattedSerial(date, id);
        console.log('fetching ' + id.toString() + '...');
        var result = getPageByDateAndId(date, id, function(html) {
            var jsonData = parseHtml.parseByText(html);
            if (callback) {
                callback(jsonData);
            }
        });
        console.log('gala get get');
        if (!result) break;
        ++id;    
    } */
    console.log('QQ');
}


module.exports = {
    'crawlWithDate': crawlWithDate
}
