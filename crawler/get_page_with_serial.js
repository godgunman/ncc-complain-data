var page = require('webpage').create(),
    args = require('system').args;

// usage
if (args.length !== 3) {
    console.log("Usage:");
    console.log("    " + args[0] + " date item");
    console.log("Format:");
    console.log("    date: year/month/day (chinese year)");
    console.log("    item: \\d+ (start from 0)");
    console.log("Example:");
    console.log("    " + args[0] + " 103/05/01 0");
    phantom.exit();
}

var delay = 4000; // ms
var target_date = args[1];
var target_item = parseInt(args[2]) % 5;
var target_page = Math.floor(parseInt(args[2]) / 5);

// go crawl
page.settings.userAgent = 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36';
page.open('https://cabletvweb.ncc.gov.tw/SWSFront35/SWSF/SWSF01017.aspx', function(status) {
    // error handle
    if (status !== 'success') {
        console.log('Unable to access network');
        phantom.exit();
    }

    // set date & trigger query
    page.evaluate(function(date) {
        document.getElementById('ctl00_ContentPlaceHolder1_Q_APPLY_DTE_ucDateTime1_dpkDate_txtDate').value = date;
        document.getElementById('ctl00_ContentPlaceHolder1_Q_APPLY_DTE_ucDateTime2_dpkDate_txtDate').value = date;
        document.getElementsByName('ctl00$ContentPlaceHolder1$Search')[0].click();
    }, target_date);

    setTimeout(function() {
        // go to target page
        if (target_page != 0) {
            page.evaluate(function(target_page) {
                // href = "javascript:__doPostBack('ctl00$ContentPlaceHolder1$dvMaster','Page$2')"
                var href = document.getElementById('ctl00_ContentPlaceHolder1_dvMaster_ctl08_dvMaster_labSplit' + target_page).parentNode.childNodes[0].href;
                href = href.replace('javascript:', '');
                eval(href);
            }, target_page);
        }

        setTimeout(function() {
            // query detail
            page.evaluate(function(target_item) {
                document.getElementById('ctl00_ContentPlaceHolder1_dvMaster_ctl0' + (target_item + 2) + '_G_View').click();
            }, target_item);

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
