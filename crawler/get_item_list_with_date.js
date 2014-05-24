var page = require('webpage').create(),
    args = require('system').args;

if (args.length !== 2) {
    console.log("Usage:");
    console.log("    " + args[0] + " date");
    console.log("Format:");
    console.log("    date: year/month/day (chinese year)");
    console.log("Example:");
    console.log("    " + args[0] + " 103/05/01");
    phantom.exit();
}

var pcnt = 0;
var delay = 2500; // ms
var fetch_date = args[1];

page.settings.userAgent = 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36';
page.open('https://cabletvweb.ncc.gov.tw/SWSFront35/SWSF/SWSF01017.aspx', function(status) {
    // error handle
    
    page.includeJs("http://code.jquery.com/jquery-1.9.1.min.js", function() {
       
    });
    if (status !== 'success') {
        console.log('Unable to access network');
        phantom.exit();
    }

    // set date & trigger query
    page.evaluate(function(date) {
        document.getElementById('ctl00_ContentPlaceHolder1_Q_APPLY_DTE_ucDateTime1_dpkDate_txtDate').value = date;
        document.getElementById('ctl00_ContentPlaceHolder1_Q_APPLY_DTE_ucDateTime2_dpkDate_txtDate').value = date;
        document.getElementsByName('ctl00$ContentPlaceHolder1$Search')[0].click();
    }, fetch_date);

    setTimeout(function() {
        // get total number of cases
        var html = page.evaluate(function() {
            return document.documentElement.innerHTML;
        });

        var item_count = parseInt(html.match(/共(\d+)筆/)[1]);
        var page_count = Math.ceil(item_count / 5);
        //console.log(item_count);

        var loop = setInterval(function() {
            //var serials;
            if (pcnt == page_count) {
                clearInterval(loop);
                return;
            }

            page.evaluate(function(page_id) {
                var href = document.getElementById('ctl00_ContentPlaceHolder1_dvMaster_ctl08_dvMaster_labSplit' 
                    + page_id).parentNode.childNodes[0].href;
                eval(href);
            }, pcnt);
            var serials;
            setTimeout(function() {
                serials = page.evaluate(function() {
                //console.log(page_id);
                var oddRows = $('.MasterGridViewItemStyle').map(
                        function(i, v) { return $(v).children()[1]; }).map(
                        function(i, v) { return $(v).text(); });
                var evenRows = $('.MasterGridViewAlternatingItemStyle').map(
                        function(i, v) { return $(v).children()[1]; }).map(
                        function(i, v) { return $(v).text(); });
                var totalRows = [];
                for (var i = 0; i < oddRows.length; ++i)
                    totalRows.push(oddRows[i]);
                for (var i = 0; i < evenRows.length; ++i)
                    totalRows.push(evenRows[i]);
                return totalRows;//.concat(evenRows);
                });
                //console.log('damn: ' + pcnt);
                for (var i = 0; i < serials.length; ++i)
                    console.log(serials[i]);
                
            }, delay)
            ++pcnt;
        }, delay);

        setTimeout(function() {
            phantom.exit();
        }, delay * (page_count + 2));
        
    }, delay);
    //
});

