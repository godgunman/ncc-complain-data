var crawler = require('./crawler/crawler');

crawler.crawlWithDate('103/05/05', function(html) {
    console.log(html);
});
