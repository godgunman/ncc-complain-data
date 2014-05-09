var request = require('request');
var cheerio = require('cheerio');

var url = 'http://w.csie.org/~b97115/file/page.html';

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

request.get(url, function(err, res, body) {

    $ = cheerio.load(body);
    console.log($('#ctl00_ContentPlaceHolder1_Label20').text());

    var result = {};
    fieldMap.forEach(function(e) {
        result[e.key] = {
            title: $("#" + e.title).text(),
            value: $("#" + e.value).text(),
        };
    });

    console.log(result);
});
