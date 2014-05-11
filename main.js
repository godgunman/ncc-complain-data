require('./config/local');
var crawler = require('./crawler/crawler');
var Complain = require('./models').Complain;

crawler.crawlWithDate('103/05/05', function(json) {
    console.log(json);
    var complain = new Complain(json);
    complain.save(function(err, complain) {
        if (err) console.error(err);
        if (complain) console.log(complain);
    });
});
