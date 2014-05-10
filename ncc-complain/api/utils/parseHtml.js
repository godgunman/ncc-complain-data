var request = require('request');
var cheerio = require('cheerio');

var fieldMap = [{
        title: 'ctl00_ContentPlaceHolder1_Label20',
        value: 'ctl00_ContentPlaceHolder1_V_SERVICE_SERIL_NO',
        key:  'id',
    }, {
        title: 'ctl00_ContentPlaceHolder1_Label1',
        value: 'ctl00_ContentPlaceHolder1_V_APPLY_DTE',
        key: 'date',
    }, {
        title: 'ctl00_ContentPlaceHolder1_Caption_Channel_Radio_Type',
        value: 'ctl00_ContentPlaceHolder1_V_CHANNAL_RADIO_TYPE',
        key: 'channelCategory',
    }, {
        title: 'ctl00_ContentPlaceHolder1_Caption_Channel_Radio_Name',
        value: 'ctl00_ContentPlaceHolder1_V_CHANNEL_RADIO_NAME',
        key: 'channelName',
    }, {
        title: 'ctl00_ContentPlaceHolder1_Label8',
        value: 'ctl00_ContentPlaceHolder1_V_SHOW_NAME',
        key: 'programName',
    }, {
        title: 'ctl00_ContentPlaceHolder1_Label2',
        value: 'ctl00_ContentPlaceHolder1_V_BROADCAST_DTE',
        key: 'broadcastDate',
    }, {
        title: 'ctl00_ContentPlaceHolder1_Label4',
        value: 'ctl00_ContentPlaceHolder1_V_BROADCAST',
        key: 'broadcastTime',
    }, {
        title: 'ctl00_ContentPlaceHolder1_Label5',
        value: 'ctl00_ContentPlaceHolder1_V_CONTENT_KIND',
        key: 'contentCategory',
    }, {
        title: 'ctl00_ContentPlaceHolder1_Label7',
        value: 'ctl00_ContentPlaceHolder1_V_CATEGORY_ID',
        key: 'complainCategory',
    }, {
        title: 'ctl00_ContentPlaceHolder1_Label12',
        value: 'ctl00_ContentPlaceHolder1_V_APPLY_SUBJECT',
        key: 'complainTitle',
    }, {
        title: 'ctl00_ContentPlaceHolder1_Label13',
        value: 'ctl00_ContentPlaceHolder1_V_APPLY_CONTENT',
        key: 'complainContent',
    },
]

function parseByURL(url, callback) {
    request.get(url, function(err, res, body) {
        $ = cheerio.load(body);
        var result = {};
        fieldMap.forEach(function(e) {
            result[e.key] = {
                title: $("#" + e.title).text(),
                value: $("#" + e.value).text(),
            };
        });
        if (callback) {
            callback(result);
        }
    });
}

function test() {
    var urls = [
        'http://w.csie.org/~b97115/file/ncc/page1.html',
        'http://w.csie.org/~b97115/file/ncc/page2.html',
        'http://w.csie.org/~b97115/file/ncc/page3.html',
        'http://w.csie.org/~b97115/file/ncc/page4.html',
        'http://w.csie.org/~b97115/file/ncc/page5.html',
        'http://w.csie.org/~b97115/file/ncc/page6.html',
        'http://w.csie.org/~b97115/file/ncc/page7.html',
        'http://w.csie.org/~b97115/file/ncc/page8.html',
        'http://w.csie.org/~b97115/file/ncc/page9.html',
        'http://w.csie.org/~b97115/file/ncc/page10.html',
    ];
    urls.forEach(function(e) {
        parseByURL(e, function(result) {
            console.log(result);
        });
    });
}

test();
