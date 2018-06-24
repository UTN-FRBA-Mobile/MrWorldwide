var express = require('express');
var app = express();
var _ = require('lodash');

const bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());


var trips = [
    {
        id: 1,
        userId: "Agustin Vertebrado",
        title: "Roma",
        tripPhoto: {
            url: "https://www.losmundosdeceli.com/wp-content/uploads/2017/05/coliseo_roma_atardecer-1080x640.jpg",
            date: "22-02-2018"
        },
        startDate: "01-03-2017",
        finishDate: "31-08-2018",
        events: [
            {
                id: 1,
                userId: "Agustin Vertebrado",
                tripId: 1,
                url: "http://thewowstyle.com/wp-content/uploads/2014/11/iStock_000012776246Small.jpg",
                date: "23-02-2018",
                description: "LALALA",
                eventType: "PHOTO",
                geoLocation: {
                    x: 1,
                    y: 2
                }
            }, 
            {
                id: 2,
                userId: "Agustin Vertebrado",
                tripId: 1,
                text: "Soy un titulo de relleno",
                date: "24-02-2018",
                title: "Soy un texto de relleno",
                eventType: "TEXT",
                geoLocation: {
                    x: 14,
                    y: 4
                }
            }, 
            {
                id: 3,
                userId: "Agustin Vertebrado",
                tripId: 1,
                date: "22-02-2018",
                description: "Soy un titulo de relleno",
                url: "https://www.youtube.com/watch?v=jdYJf_ybyVo&list=RDjdYJf_ybyVo&start_radio=1&asv=2",
                eventType: "VIDEO",
                geoLocation: {
                    x: 1,
                    y: 2
                }
            }
        ]
    }, 
    {
        id: 2,
        userId: "Mercedes Hidratada",
        title: "New York",
        tripPhoto: {
            url: "https://brightcove04pmdo-a.akamaihd.net/5104226627001/5104226627001_5244714388001_5205235439001-vs.jpg?pubId=5104226627001&videoId=5205235439001",
            date: "18-02-2018"
        },
        startDate: "01-02-2018",
        finishDate: "17-02-2018",
        events: [{
            id: 4,
            userId: "Mercedes Hidratada",
            tripId: 2,
            url: "http://www.zastavki.com/pictures/originals/2014/Nature___Desert_The_lonely_traveler_in_the_desert_089486_.jpg",
            date: "22-12-2018",
            description: "mira mama, mira!",
            eventType: "PHOTO",
            geoLocation: {
                x: 1,
                y: 2
            }
        }]
    }, 
    {
        id: 3,
        userId: "Agustin Vertebrado",
        title: "New York",
        tripPhoto: {
            url: "https://brightcove04pmdo-a.akamaihd.net/5104226627001/5104226627001_5244714388001_5205235439001-vs.jpg?pubId=5104226627001&videoId=5205235439001",
            date: "18-02-2018"
        },
        startDate: "01-02-2018",
        finishDate: "17-02-2018",
        events: []
    }, 
    {
        id: 4,
        userId: "Esteban Piro",
        title: "New York",
        tripPhoto: {
            url: "https://brightcove04pmdo-a.akamaihd.net/5104226627001/5104226627001_5244714388001_5205235439001-vs.jpg?pubId=5104226627001&videoId=5205235439001",
            date: "18-02-2018"
        },
        startDate: "01-02-2018",
        finishDate: "17-02-2018",
        events: [{
            id: 5,
            userId: "Esteban Piro",
            tripId: 4,
            text: "Soy un titulo de relleno",
            date: "24-02-2018",
            title: "Soy un texto de relleno",
            eventType: "TEXT",
            geoLocation: {
                x: 14,
                y: 4
            }
        }]
    }, 
    {
        id: 5,
        userId: "Pepe Lotas",
        title: "New York",
        tripPhoto: {
            url: "https://brightcove04pmdo-a.akamaihd.net/5104226627001/5104226627001_5244714388001_5205235439001-vs.jpg?pubId=5104226627001&videoId=5205235439001",
            date: "18-02-2018"
        },
        startDate: "01-02-2018",
        finishDate: "17-02-2018",
        events: [{
            id: 6,
            userId: "Pepe Lotas",
            tripId: 5,
            date: "22-02-2018",
            description: "Soy un titulo de relleno",
            url: "https://www.youtube.com/watch?v=jdYJf_ybyVo&list=RDjdYJf_ybyVo&start_radio=1&asv=2",
            eventType: "VIDEO",
            geoLocation: {
                x: 1,
                y: 2
            }
        }]
    }

];

var events = [
    {
        id: 1,
        userId: "Agustin Vertebrado",
        tripId: 1,
        url: "http://thewowstyle.com/wp-content/uploads/2014/11/iStock_000012776246Small.jpg",
        date: "23-02-2018",
        description: "LALALA",
        eventType: "PHOTO",
        geoLocation: {
            x: 1,
            y: 2
        }
    }, 
    {
        id: 2,
        userId: "Agustin Vertebrado",
        tripId: 1,
        text: "Soy un texto de relleno",
        date: "24-02-2018",
        title: "Soy un titulo de relleno",
        eventType: "TEXT",
        geoLocation: {
            x: 14,
            y: 4
        }
    }, 
    {
        id: 3,
        userId: "Agustin Vertebrado",
        tripId: 1,
        date: "22-02-2018",
        description: "Soy un titulo de relleno",
        url: "https://www.youtube.com/watch?v=jdYJf_ybyVo&list=RDjdYJf_ybyVo&start_radio=1&asv=2",
        eventType: "VIDEO",
        geoLocation: {
            x: 1,
            y: 2
        }
    },
    {
        id: 4,
        userId: "Mercedes Hidratada",
        tripId: 2,
        url: "http://www.zastavki.com/pictures/originals/2014/Nature___Desert_The_lonely_traveler_in_the_desert_089486_.jpg",
        date: "22-12-2018",
        description: "mira mama, mira!",
        eventType: "PHOTO",
        geoLocation: {
            x: 1,
            y: 2
        }
    },
    {
        id: 5,
        userId: "Esteban Piro",
        tripId: 4,
        text: "Soy un titulo de relleno",
        date: "24-02-2018",
        title: "Soy un texto de relleno",
        eventType: "TEXT",
        geoLocation: {
            x: 14,
            y: 4
        }
    },
    {
        id: 6,
        userId: "Pepe Lotas",
        tripId: 5,
        date: "22-02-2018",
        description: "Soy un titulo de relleno",
        url: "https://www.youtube.com/watch?v=jdYJf_ybyVo&list=RDjdYJf_ybyVo&start_radio=1&asv=2",
        eventType: "VIDEO",
        geoLocation: {
            x: 1,
            y: 2
        }
    }
]

var friendEvents = [{
        id: 4,
        userId: "Mercedes Hidratada",
        tripId: 2,
        url: "http://www.zastavki.com/pictures/originals/2014/Nature___Desert_The_lonely_traveler_in_the_desert_089486_.jpg",
        date: "22-12-2018",
        description: "mira mama, mira!",
        eventType: "PHOTO",
        geoLocation: {
            x: 1,
            y: 2
        }
    }, {
        id: 5,
        userId: "Esteban Piro",
        tripId: 4,
        text: "Soy un titulo de relleno",
        date: "24-02-2018",
        title: "Soy un texto de relleno",
        eventType: "TEXT",
        geoLocation: {
            x: 14,
            y: 4
        }
    }, {
        id: 6,
        userId: "Pepe Lotas",
        tripId: 5,
        date: "22-02-2018",
        description: "Soy un titulo de relleno",
        url: "https://www.youtube.com/watch?v=jdYJf_ybyVo&list=RDjdYJf_ybyVo&start_radio=1&asv=2",
        eventType: "VIDEO",
        geoLocation: {
            x: 1,
            y: 2
        }
    }

];
var userTrips = function(userID) {
    return _.filter(trips, function(trip) {
        return trip.userId === userID
    });
};

var stringToDate = function(stringDate) {
    var parts = stringDate.split('-');
    return new Date(parts[2], parts[1] - 1, parts[0]);
};

var tripsWithoutEvents = function(trip) {
    var tripCopy = _.assign({}, trip);
    tripCopy.events = [];
    return tripCopy;
};

var getActualTrip = function(userId) {
    var actualTrips = _.filter(userTrips(userId), function(trip) {
        return stringToDate(trip.startDate) <= Date.now() && stringToDate(trip.finishDate) >= Date.now()
    });
    var orderedActualTrips = _.sortBy(actualTrips, function(trip) {
        return stringToDate(trip.startDate)
    })
    return orderedActualTrips[0];
}

app.get('/', function(req, res) {
    res.send('Hello World!');
});

app.get('/users/:userId/trips', function(req, res) {
    res.send(_.map(_.filter(trips, function(trip) {
        return trip.userId === req.params.userId
    }), tripsWithoutEvents));
});

app.get('/trips/:id', function(req, res) {
    res.send(_.find(trips, function(trip) {
        return trip.id === Number(req.params.id)
    }));
});

app.get('/users/:userId/nextTrip', function(req, res) {
    var nextUserTrips = _.filter(userTrips(req.params.userId), function(trip) {
        return stringToDate(trip.startDate) > Date.now()
    });
    res.send(_.sortBy(nextUserTrips, function(trip) {
        return stringToDate(trip.startDate)
    })[0]);
});

app.get('/users/:userId/actualTrip', function(req, res) {
    res.send(getActualTrip(req.params.userId));
});

app.get('/users/:userId/friendEvents', function(req, res) {
    res.send(friendEvents);
});

app.get('/event/:userId/:tripId/:eventId', function(req, res) {
    var userId = req.params.userId;
    var tripId = Number(req.params.tripId);
    var eventId = Number(req.params.eventId);
    var trip = _.find(trips, function(aTrip) {
        return aTrip.id === tripId && aTrip.userId === userId;
    });
    var event = _.find(trip.events, function(event) {
        return event.id === eventId;
    })
    res.send(event);
});

app.post('/trips/:userId', function(req, res) {
    var newTrip = req.body;
    newTrip.id = _.maxBy(trips, 'id').id + 1;
    newTrip.userId = req.params.userId;
    trips.push(newTrip);
    res.send(trips);
});

app.delete('trips/:id', function(req, res) {
    trips = _.delete(trips, function(trip) {
        return trip.id == req.params.id
    });
    res.send(trips);
});

app.listen(3000, function() {
    console.log('Example app listening on port 3000!');
});

//////////////////ENDPOINTS PENDIENTES//////////////////////////////
app.get('/users/:userId/events', function(req, res) {
    //res.send(_.map(_.filter(events,function (event){return event.userId === Number(req.params.userId)}),tripsWithoutEvents));
});

app.get('/events/:id', function(req, res) {
    //res.send(_.find(events,function (event){return  event.id === Number(req.params.id)}));
});

app.delete('events/:id', function(req, res) {
    //_.delete(events, function(event){ return event.id == req.params.id });
});