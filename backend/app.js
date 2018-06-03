var express = require('express');
var app = express();
var _ = require('lodash');

var trips = [
    {
        id: 1,
        userId:2,
        title: "Roma",
        tripPhoto: {
            url: "https://www.losmundosdeceli.com/wp-content/uploads/2017/05/coliseo_roma_atardecer-1080x640.jpg",
            date: "22-02-2018"
        },
        startDate: "01-03-2017",
        finishDate: "31-08-2017",
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
        userId:2,
        title : "New York",
        tripPhoto : {
            url : "https://brightcove04pmdo-a.akamaihd.net/5104226627001/5104226627001_5244714388001_5205235439001-vs.jpg?pubId=5104226627001&videoId=5205235439001",
            date : "18-02-2018"
        },
        startDate: "01-07-2017",
        finishDate: "17-012-2017",
        events: []
    },
    {
        id : 3,
        userId:3,
        title : "Bs.As",
        tripPhoto : {
            url : "https://media-cdn.tripadvisor.com/media/photo-s/0e/8f/63/29/obelisco-buenos-aires.jpg",
            date : "18-02-2018"
        },
        startDate: "01-02-2018",
        finishDate: "17-02-2018",
        events: []
    }
];

var userTrips = function(userID){
    return _.filter(trips,function(trip){return trip.userId === userID});
};

var stringToDate = function(stringDate){
    var parts =  stringDate.split('-');
    return new Date(parts[2], parts[1]-1, parts[0]);
};

var tripsWithoutEvents = function(trip){
    var tripCopy = _.assign({}, trip);
    tripCopy.events = [];
    return tripCopy;
};

app.get('/', function (req, res) {
    res.send('Hello World!');
});

app.get('/trips/:userId', function(req, res) {
    res.send(_.map(_.filter(trips,function (trip){return trip.userId === Number(req.params.userId)}),tripsWithoutEvents));
});

app.get('/trip/:id',function(req,res){
    res.send(_.find(trips,function (trip){return  trip.id === Number(req.params.id)}));
});

app.get('/nextTrip/:userId',function(req,res){
    var nextUserTrips = _.filter(userTrips(Number(req.params.userId)),function(trip){return stringToDate(trip.startDate) > Date.now()});
    res.send(_.sortBy(nextUserTrips,function(trip){return stringToDate(trip.startDate)})[0]);
});

app.get('/actualTrip/:userId',function(req,res){
    var actualTrips = _.filter(userTrips(Number(req.params.userId)),function(trip){return stringToDate(trip.startDate) <= Date.now() && stringToDate(trip.finishDate) >= Date.now()});
    res.send(_.sortBy(actualTrips,function(trip){return stringToDate(trip.startDate)})[0]);
});

app.listen(3000, function () {
    console.log('Example app listening on port 3000!');
});
