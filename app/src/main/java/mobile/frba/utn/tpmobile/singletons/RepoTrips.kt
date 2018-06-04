package mobile.frba.utn.tpmobile.singletons

import com.google.android.gms.maps.model.LatLng
import mobile.frba.utn.tpmobile.models.*
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.DateTime

object RepoTrips{
     var trips: MutableList<Trip> = ArrayList()

    init{
        val photo = Photo("asd", DateTime.now(), "LALALALA", LatLng(40.7,-74.9))
        val text = Text("Soy un titulo de relleno", DateTime.now(),"Soy un texto de relleno",LatLng(40.7,-73.9))
        val video = Video("Soy un titulo de relleno", "saraza",LatLng(41.7,-73.9))

        val romaPhoto = TripPhoto("https://www.losmundosdeceli.com/wp-content/uploads/2017/05/coliseo_roma_atardecer-1080x640.jpg", DateTime.now(),LatLng(40.7,-73.9))
        val romaTrip = Trip(1,"Roma", romaPhoto, DateTime().withDate(2018, 2, 10), DateTime().withDate(2018, 2, 22), mutableListOf())
        romaTrip.events.addAll(romaTrip.events.lastIndex+1, mutableListOf(photo,text,video))
        val nyPhoto = TripPhoto("https://brightcove04pmdo-a.akamaihd.net/5104226627001/5104226627001_5244714388001_5205235439001-vs.jpg?pubId=5104226627001&videoId=5205235439001", DateTime.now(), LatLng(40.0,0.0))
        val nyTrip = Trip(2,"New York", nyPhoto, DateTime().withDate(2019, 2, 10), DateTime().withDate(2019, 2, 22), mutableListOf())
        val photoTk = Photo("asd", DateTime.now(), "LALALALA", LatLng(35.6,139.8))
        val textTk = Text("Soy un titulo de relleno", DateTime.now(),"Soy un texto de relleno",LatLng(36.6,139.8))
        val videoTk = Video("Soy un titulo de relleno", "saraza",LatLng(35.6,140.8))
        val tkPhoto = TripPhoto("https://www.hola.com/imagenes/viajes/20180115104660/tokio-barrio-a-barrio-japon/0-527-396/tokio-Shibuya-t.jpg", DateTime.now(), LatLng(35.6,139.8))
        val tkTrip = Trip(3,"Tokio", tkPhoto, DateTime().withDate(2018, 2, 10), DateTime().withDate(2018, 2, 22), mutableListOf())
        tkTrip.events.addAll(tkTrip.events.lastIndex+1, mutableListOf(videoTk,textTk,photoTk))


        this.addTrip(romaTrip)
        this.addTrip(nyTrip)
        this.addTrip(tkTrip)
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