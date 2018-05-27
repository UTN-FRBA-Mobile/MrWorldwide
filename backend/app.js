var express = require('express');
var app = express();


var trips = [
    {
        id: 1,
        title: "Roma",
        tripPhoto: {
            url: "https://www.losmundosdeceli.com/wp-content/uploads/2017/05/coliseo_roma_atardecer-1080x640.jpg",
            date: "22-02-2018"
        },
        startDate: "01-02-2018",
        finishDate: "31-06-2018",
        events: [
            {
                url : "asd",
                date : "23-02-2018",
                description : "LALALA",
                eventType : "PHOTO"
            },
            {
                text : "Soy un titulo de relleno",
                date : "24-02-2018",
                title : "Soy un texto de relleno",
                eventType : "TEXT"
            },
            {
                description : "Soy un titulo de relleno",
                url : "saraza",
		eventType : "VIDEO"
            }
        ]
    },
    {
        id : 2,
        title : "New York",
        tripPhoto : {
            url : "https://brightcove04pmdo-a.akamaihd.net/5104226627001/5104226627001_5244714388001_5205235439001-vs.jpg?pubId=5104226627001&videoId=5205235439001",
            date : "18-02-2018"
        },
        startDate: "01-02-2018",
        finishDate: "17-02-2018",
        events: []
    }
];

app.get('/', function (req, res) {
    res.send('Hello World!');
});

app.get('/trips', function(req, res) {
    res.send(trips);
});

app.listen(3000, function () {
    console.log('Example app listening on port 3000!');
});
