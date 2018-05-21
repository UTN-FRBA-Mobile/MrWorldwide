package mobile.frba.utn.tpmobile.Singletons

import mobile.frba.utn.tpmobile.models.*
import org.joda.time.DateTime

open class RepoTrips{
     var trips: MutableList<Trip> = ArrayList()

    private constructor(){
        val photo = Photo("asd", DateTime.now(), "LALALALA")
        val text = Text("Soy un titulo de relleno", DateTime.now(),"Soy un texto de relleno")
        val video = Video("Soy un titulo de relleno", "saraza")

        val romaPhoto = TripPhoto("https://www.losmundosdeceli.com/wp-content/uploads/2017/05/coliseo_roma_atardecer-1080x640.jpg", DateTime.now())
        val romaTrip = Trip(1,"Roma", romaPhoto, DateTime().withDate(2018, 2, 10), DateTime().withDate(2018, 2, 22), mutableListOf())
        romaTrip.events.addAll(romaTrip.events.lastIndex+1, mutableListOf(photo,text,video))
        val nyPhoto = TripPhoto("https://brightcove04pmdo-a.akamaihd.net/5104226627001/5104226627001_5244714388001_5205235439001-vs.jpg?pubId=5104226627001&videoId=5205235439001", DateTime.now())
        val nyTrip = Trip(2,"New York", nyPhoto, DateTime().withDate(2019, 2, 10), DateTime().withDate(2019, 2, 22), mutableListOf())

        this.addTrip(romaTrip)
        this.addTrip(nyTrip)
    }

    companion object Factory {
         var repo = RepoTrips()
    }

    fun addTrip(trip : Trip){
        trips.add(trips.lastIndex + 1,trip)
    }

    fun getTrip(id: Int){
        trips.find { trip -> trip.id == id  }
    }

    fun getActualTripFor(userId : Int): Trip? {
        return trips.firstOrNull { trip -> trip.startDate <= DateTime.now() && trip.finishDate >= DateTime.now() }
    }

    fun getNextTripFor(userId : Int): Trip? {
        trips.sortBy { trip -> trip.startDate  }
        return trips.firstOrNull {trip -> trip.startDate > DateTime.now()}
    }

}