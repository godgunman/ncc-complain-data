var request = require('request');
var cheerio = require('cheerio');
var Q = require('q');

var fieldMap = [{
        title: 'ctl00_ContentPlaceHolder1_Label20',
        value: 'ctl00_ContentPlaceHolder1_V_SERVICE_SERIL_NO',
        key:  'cid',
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
     {
        title: 'ctl00_ContentPlaceHolder1_Label14',
        value: 'ctl00_ContentPlaceHolder1_V_REPLY_CONTENT',
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

function parseByText(text) {
    $ = cheerio.load(text);
    var result = {};
    fieldMap.forEach(function(e) {
        result[e.key] = $("#" + e.value).text();
    });
    return result;
}

function test(callback) {
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
    var results = [];
    var promises = [];

    urls.forEach(function(e) {
        promises.push(function(){
            var deferred = Q.defer();
            parseByURL(e, function(result) {
                results.push(result);
                deferred.resolve(result);
            });
            return deferred.promise;
        }());
    });
    if (callback) {
        Q.all(promises).then(function() {
            callback(results);
        });
    }
}

module.exports = {
    'parseByText': parseByText,
    'parseByURL' : parseByURL,
    'test': test,
}
