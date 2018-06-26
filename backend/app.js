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
        events: [],
        startDate: "01-03-2017",
        finishDate: "31-08-2018"
    },
  {
        id: 2,
        userId: "Mercedes Hidratada",
        title: "New York",
        tripPhoto: {
            url: "https://brightcove04pmdo-a.akamaihd.net/5104226627001/5104226627001_5244714388001_5205235439001-vs.jpg?pubId=5104226627001&videoId=5205235439001",
            date: "18-02-2018"
        },
        events: [],
        startDate: "01-02-2018",
        finishDate: "17-02-2018"
    },
    {
        id: 3,
        userId: "Agustin Vertebrado",
        title: "New York",
        tripPhoto: {
            url: "https://brightcove04pmdo-a.akamaihd.net/5104226627001/5104226627001_5244714388001_5205235439001-vs.jpg?pubId=5104226627001&videoId=5205235439001",
            date: "18-02-2018"
        },
        events: [],
        startDate: "01-02-2018",
        finishDate: "17-02-2018"
    },
    {
        id: 4,
        userId: "Esteban Piro",
        title: "New York",
        tripPhoto: {
            url: "https://brightcove04pmdo-a.akamaihd.net/5104226627001/5104226627001_5244714388001_5205235439001-vs.jpg?pubId=5104226627001&videoId=5205235439001",
            date: "18-02-2018"
        },
        events: [],
        startDate: "01-02-2018",
        finishDate: "17-02-2018"
    },
    {
        id: 5,
        userId: "Pepe Lotas",
        title: "New York",
        tripPhoto: {
            url: "https://brightcove04pmdo-a.akamaihd.net/5104226627001/5104226627001_5244714388001_5205235439001-vs.jpg?pubId=5104226627001&videoId=5205235439001",
            date: "18-02-2018"
        },
        events: [],
        startDate: "01-02-2018",
        finishDate: "17-02-2018"
    }

];

var events = [
    {
        id: 1,
        userId: "Agustin Vertebrado",
        tripId: 1,
        title: "El título",
        url: "http://thewowstyle.com/wp-content/uploads/2014/11/iStock_000012776246Small.jpg",
        date: "23-02-2018",
        description: "LALALA",
        eventType: "PHOTO",
        likes: ["Mercedes Hidratada", "Esteban Piro", "Pepe Lotas"],
        geoLocation: {
            latitude: 1,
            longitude: 2
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
        likes: ["Mercedes Hidratada"],
        geoLocation: {
            latitude: 14,
            longitude: 4
        }
    },
    /*{
        id: 3,
        userId: "Agustin Vertebrado",
        tripId: 1,
        date: "22-02-2018",
        description: "Soy un titulo de relleno",
        title: "Soy un titulo de relleno",
        url: "https://www.youtube.com/watch?v=jdYJf_ybyVo&list=RDjdYJf_ybyVo&start_radio=1&asv=2",
        eventType: "VIDEO",
        likes: [],
        geoLocation: {
            latitude: 1,
            longitude: 2
        }
    },*/
    {
        id: 4,
        userId: "Mercedes Hidratada",
        tripId: 2,
        title: "El título",
        url: "http://www.zastavki.com/pictures/originals/2014/Nature___Desert_The_lonely_traveler_in_the_desert_089486_.jpg",
        date: "22-12-2018",
        description: "mira mama, mira!",
        eventType: "PHOTO",
        likes: [],
        geoLocation: {
            latitude: 1,
            longitude: 2
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
        likes: [],
        geoLocation: {
            latitude: 14,
            longitude: 4
        }
    }
]

var friendEvents = [{
        id: 4,
        userId: "Mercedes Hidratada",
        tripId: 2,
        title: "El título",
        url: "http://www.zastavki.com/pictures/originals/2014/Nature___Desert_The_lonely_traveler_in_the_desert_089486_.jpg",
        date: "22-12-2018",
        description: "mira mama, mira!",
        eventType: "PHOTO",
        likes: [],
        geoLocation: {
            latitude: 1,
            longitude: 2
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
        likes: [],
        geoLocation: {
            latitude: 14,
            longitude: 4
        }
    },
    {
        id: 6,
        userId: "Pepe Lotas",
        tripId: 5,
        date: "22-02-2018",
        title: "SARACATUNGA",
        description: "Soy una descripcion de relleno",
        url: "https://www.youtube.com/watch?v=jdYJf_ybyVo&list=RDjdYJf_ybyVo&start_radio=1&asv=2",
        eventType: "VIDEO",
        likes: [],
        geoLocation: {
            latitude: 1,
            longitude: 2
        }
    }
]

var friends = [
    "Mercedes Hidratada",
    "Esteban Piro",
    "Pepe Lotas"
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

var completeTripEvents = function(trip){
    tripCopy = _.assign({}, trip);
    tripCopy.events = _.filter(events,function(event){
        return event.tripId === tripCopy.id && event.userId === tripCopy.userId;
    })
    return tripCopy;
}

var friendEvents = function(){
    return _.filter(events,function(event){
        return _.some(friends,function(friend){
            return friend === event.userId;
        })
    })
}
app.get('/', function(req, res) {
    res.send('Hello World!');
});

app.get('/users/:userId/trips', function(req, res) {
    res.send(_.map(_.filter(trips, function(trip) {
        return trip.userId === req.params.userId
    }), tripsWithoutEvents));
});

app.get('/users/:userId/tripsWithEvents', function(req, res) {
    trips = _.filter(trips,function (trip){return trip.userId === req.params.userId});
    res.send(_.map(trips,function(trip){
        return completeTripEvents(trip);
    }));
});

app.get('/trips/:id', function(req, res) {
    trip = _.find(trips, function(trip) {
                   return trip.id === Number(req.params.id)
               });
    res.send(completeTripEvents(trip));
});

app.get('/users/:userId/nextTrip', function(req, res) {
    var nextUserTrips = _.filter(userTrips(req.params.userId), function(trip) {
        return stringToDate(trip.startDate) > Date.now()
    });
    res.send(completeTripEvents(_.sortBy(nextUserTrips, function(trip) {
        return stringToDate(trip.startDate)
    })[0]));
});

app.get('/users/:userId/actualTrip', function(req, res) {
    res.send(completeTripEvents(getActualTrip(req.params.userId)));
});

app.get('/users/:userId/friendEvents', function(req, res) {
    res.send(friendEvents());
});

app.get('/event/:userId/:tripId/:eventId', function(req, res) {
    var userId = req.params.userId;
    var tripId = Number(req.params.tripId);
    var eventId = Number(req.params.eventId);
    var event = _.find(events, function(event) {
        return event.id === eventId && userId === event.userId && tripId === event.tripId;
    })
    res.send(event);
});

app.post('/event/:userId/:tripId/:eventId/mg', function(req, res) {
    var userId = req.params.userId;
    var tripId = Number(req.params.tripId);
    var eventId = Number(req.params.eventId);

    var event = _.find(events, function(event) {
        return event.id === eventId && event.userId === userId && event.tripId === tripId;
    })

    console.log(req.body)

    event.likes.push(req.body.userId);
    res.send(event);
});

app.post('/trips/:userId', function(req, res) {
    var newTrip = req.body;
    newTrip.id = _.maxBy(trips, 'id').id + 1;
    newTrip.userId = req.params.userId;
    trips.push(newTrip);
    res.send(trips);
});

app.put('/trips/:tripId', function(req, res) {
    var updatedTrip = req.body;
    var index = _.findIndex(trips, function(trip) {
        return trip.id == req.params.tripId
    }) 
    var trip = trips[index];
    updatedTrip.id = trip.id;
    updatedTrip.userId = trip.userId
    updatedTrip.events = trip.events
    trips[index] = updatedTrip;
    res.send(trips);
});

app.put('/events/:eventId', function(req, res) {
    var updatedEvent = req.body;
    var index = _.findIndex(events, function(event) {
        return event.id == req.params.eventId
    }) 
    var event = events[index];
    updatedEvent.id = event.id;
    updatedEvent.userId = event.userId
    updatedEvent.tripId = event.tripId
    updatedEvent.events = event.events
    updatedEvent.likes = event.likes
    updatedEvent.geoLocation = event.geoLocation
    updatedEvent.geoLocation = event.geoLocation


    events[index] = updatedEvent;
    res.send(events);
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

app.post('/events/:userId', function(req, res) {
    var newEvent = req.body;
    var actualTrip = getActualTrip(req.params.userId);
    newEvent.id = _.maxBy(events, 'id').id + 1;
    newEvent.userId = req.params.userId;
    newEvent.tripId = actualTrip.id;
    events.push(newEvent);
    res.send(events);
});

app.delete('events/:id', function(req, res) {
    events = _.delete(events, function(event) {
        return event.id == req.params.id
    });
    res.send(trips);
});
