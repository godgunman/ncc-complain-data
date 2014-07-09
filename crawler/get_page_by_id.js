
function parseDate(cid) {
    var year = cid.slice(0, 4);
    var month = cid.slice(4, 6);
    var day = cid.slice(6, 8);
    return year + '/' + month + '/' + day;
}

var page = require('webpage').create(),
args = require('system').args;

//console.log('jumi');

// usage
if (args.length !== 2) {
    console.log("Usage:");
    console.log("    " + args[0] + " year cid");
    console.log("Example:");
    console.log("    " + args[0] + " 20140524T07122");
    phantom.exit();
}

var delay = 4000; // ms
var target_cid = args[1];
var target_date = parseDate(target_cid);

//console.log(target_date);
//console.log(target_serial);
//var target_page = Math.floor(parseInt(args[2]) / 5);

// go crawl
page.settings.userAgent = 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36';
page.settings.resourceTimeout = 5000;
var ncc_url = 'https://cabletvweb.ncc.gov.tw/SWSFront35/SWSF/SWSF01017.aspx';
page.open(ncc_url, function(status) {
    page.includeJs("http://code.jquery.com/jquery-1.9.1.min.js", function() {

    });
    // error handle
    if (status !== 'success') {
        console.log('Unable to access network');
        phantom.exit();
    }

    // set date & trigger query
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
    }, target_cid, target_date);
    //console.log(target_date);

    //console.log(target_cid);

    setTimeout(function() {
        setTimeout(function() {
            var stat = page.evaluate(function() {
                var st = $('.MasterGridViewItemStyle').map(
                    function(i, v) { return $(v).children()[8]; }).map(
                    function(i, v) { return $(v).text(); });

                    document.getElementById('ctl00_ContentPlaceHolder1_dvMaster_ctl02_G_View').click();
                    return st;
            });
            //console.log(stat.length);
            //console.log(stat[0]);
            if (stat[0] == '處理中') console.log('__data__inject__status=pending');
            else console.log('__data__inject__status=done');
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
