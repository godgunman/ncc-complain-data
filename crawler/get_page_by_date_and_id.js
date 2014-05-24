

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

function getFormattedSerial(year, month, date, id) {
    //console.log(date.toString());
    var yearStr = year.toString();
    var monthStr = fillZero(month, 2);
    var dayStr = fillZero(date, 2);
    var serialStr = yearStr + monthStr + dayStr + 'T' + fillZero(id, 5);
    return serialStr;
}

function getFormattedDate(year, month, date) {
    var yearStr = year.toString();
    var monthStr = fillZero(month, 2);
    var dayStr = fillZero(date, 2);
    //var serialStr = yearStr + monthStr + dayStr + 'T' + fillZero(id, 5);
    return yearStr + '/' + monthStr + '/' + dayStr;
}

var page = require('webpage').create(),
    args = require('system').args;

//console.log('jumi');

// usage
if (args.length !== 5) {
    console.log("Usage:");
    console.log("    " + args[0] + " year month date id");
    console.log("Example:");
    console.log("    " + args[0] + " 2014 5 24 1");
    phantom.exit();
}

var delay = 4000; // ms
var target_year = parseInt(args[1]);
var target_month = parseInt(args[2]);
var target_day = parseInt(args[3]);
var target_id   = parseInt(args[4]);
var target_date = getFormattedDate(target_year, 
                                   target_month, 
                                   target_day);
var target_serial = getFormattedSerial(target_year, 
                                   target_month, 
                                   target_day,
                                   target_id);

//console.log(target_date);
//console.log(target_serial);
//var target_page = Math.floor(parseInt(args[2]) / 5);

// go crawl
page.settings.userAgent = 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36';
page.settings.resourceTimeout = 5000;
var ncc_url = 'https://cabletvweb.ncc.gov.tw/SWSFront35/SWSF/SWSF01017.aspx';
var google_url = 'https://www.google.com/'
page.open(ncc_url, function(status) {
    // error handle
    //console.log(status);
    if (status !== 'success') {
        console.log('Unable to access network');
        phantom.exit();
    }

    // set date & trigger query
    console.log(target_serial);
    page.evaluate(function(serial, date) {
        var serialColumnId = 'ctl00_ContentPlaceHolder1_Q_SERVICE_SERIL_NO_EditText';
        var dateStartColumnId = 'ctl00_ContentPlaceHolder1_Q_APPLY_DTE_ucDateTime1_dpkDate_txtDate';
        var dateEndColumnId = 'ctl00_ContentPlaceHolder1_Q_APPLY_DTE_ucDateTime2_dpkDate_txtDate';
        var clickButtonId = 'ctl00$ContentPlaceHolder1$Search';
        //console.log(serial);
        document.getElementById(serialColumnId).value = serial;
        document.getElementById(dateStartColumnId).value = date;
        document.getElementById(dateEndColumnId).value = date;
        document.getElementsByName(clickButtonId)[0].click();
    }, target_serial, target_date);

    setTimeout(function() {
        setTimeout(function() {
            // query detail
            page.evaluate(function() {
                document.getElementById('ctl00_ContentPlaceHolder1_dvMaster_ctl02_G_View').click();
            });

            setTimeout(function() {
                var html = page.evaluate(function() {
                    return document.documentElement.innerHTML;
                });
                console.log(html);
                phantom.exit();
            }, delay);
        }, delay);
    }, delay);
});
