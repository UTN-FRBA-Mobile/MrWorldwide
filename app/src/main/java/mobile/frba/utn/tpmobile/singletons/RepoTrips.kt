package mobile.frba.utn.tpmobile.singletons

import com.google.android.gms.maps.model.LatLng
import mobile.frba.utn.tpmobile.models.*
import okhttp3.*
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.DateTime
import org.json.JSONArray
import java.io.IOException

object RepoTrips{
     var trips: MutableList<Trip> = ArrayList()
     var backUrl = "http://10.0.2.2:3000"
     var userId = 2
     val client = OkHttpClient()

    init{
        val photo = Photo("asd", DateTime.now(), "LALALALA", LatLng(40.7,-74.9))
        val text = Text("Soy un titulo de relleno", DateTime.now(),"Soy un texto de relleno",LatLng(40.7,-73.9))
        val video = Video("Soy un titulo de relleno", "saraza",LatLng(41.7,-73.9))

        val romaPhoto = TripPhoto("https://www.losmundosdeceli.com/wp-content/uploads/2017/05/coliseo_roma_atardecer-1080x640.jpg", DateTime.now(),LatLng(40.7,-73.9))
        val romaTrip = Trip(1,"Roma", romaPhoto, DateTime().withDate(2018, 2, 10), DateTime().withDate(2018, 2, 22), mutableListOf())
        romaTrip.events.addAll(romaTrip.events.lastIndex+1, mutableListOf(photo,text,video))
        val nyPhoto = TripPhoto("https://brightcove04pmdo-a.akamaihd.net/5104226627001/5104226627001_5244714388001_5205235439001-vs.jpg?pubId=5104226627001&videoId=5205235439001", DateTime.now())
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


    fun getTrips() : ((MutableList<Trip>)-> Unit)->Unit{
        return { callback ->
            run {
                client.newCall(Request.Builder().url("$backUrl/trips/$userId").build())
                        .enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                throw Error("rompio todo!")
                            }

                            override fun onResponse(call: Call, response: Response) {
                                val jsonTrips = JSONArray(response.body()!!.string())

                                var x = 0
                                val trips: MutableList<Trip> = emptyArray<Trip>().toMutableList()
                                while (x < jsonTrips.length()) {
                                    val trip = jsonTrips.getJSONObject(x)
                                    trips.add(Trip.getFromJson(trip))
                                    x++
                                }
                                callback.invoke(trips)
                            }
                        })
            }
        }
    }
    fun addTrip(trip : Trip){
        trips.add(trips.lastIndex + 1,trip)
    }

    fun getTrip(id: Int): ((Trip?)-> Unit)->Unit{
        return { callback ->
            run {
                client.newCall(Request.Builder().url("$backUrl/trip/$id").build())
                        .enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                throw Error("rompio todo!")
                            }

                            override fun onResponse(call: Call, response: Response) {

                                val trip = Trip.getFromString(response.body()!!.string())

                                callback.invoke(trip)
                            }
                        })
            }
        }
    }

    fun getActualTripFor(): ((Trip?)-> Unit)->Unit {
        return { callback ->
            run {
                client.newCall(Request.Builder().url("$backUrl/actualTrip/$userId").build())
                        .enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                throw Error("rompio todo!")
                            }

                            override fun onResponse(call: Call, response: Response) {

                                val trip = Trip.getFromString(response.body()!!.string())

                                callback.invoke(trip)
                            }
                        })
            }
        }
    }

    fun getNextTripFor(): ((Trip?)-> Unit)->Unit {
        return { callback ->
            run {
                client.newCall(Request.Builder().url("$backUrl/nextTrip/$userId").build())
                        .enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                throw Error("rompio todo!")
                            }

                            override fun onResponse(call: Call, response: Response) {
                                val trip = Trip.getFromString(response.body()!!.string())
                                callback.invoke(trip)
                            }
                        })
            }
        }
    }
}