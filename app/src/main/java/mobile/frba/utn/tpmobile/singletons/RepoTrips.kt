package mobile.frba.utn.tpmobile.singletons

import com.github.kittinunf.fuel.httpPost
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import mobile.frba.utn.tpmobile.models.*
import okhttp3.*
import org.joda.time.DateTime
import org.json.JSONArray
import java.io.IOException

object RepoTrips{
     var trips: MutableList<Trip> = ArrayList()
     var backUrl = "http://10.0.2.2:3000"
     var userId = "Agustin Vertebrado"
     val client = OkHttpClient()

    fun getTrips() : ((MutableList<Trip>)-> Unit)->Unit{
        return { callback ->
            run {
                client.newCall(Request.Builder().url("$backUrl/users/$userId/trips").build())
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
    fun addTrip(trip: Trip, callback: () -> Unit){
        trips.add(trips.lastIndex + 1,trip)
        "$backUrl/trips/$userId".httpPost().body(Gson().toJson(trip)).response({ _, _, result ->
            println(result)
            callback.invoke()})
    }

    fun getTrip(id: Int): ((Trip?)-> Unit)->Unit{
        return { callback ->
            run {
                client.newCall(Request.Builder().url("$backUrl/trips/$id").build())
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
                client.newCall(Request.Builder().url("$backUrl/users/$userId/actualTrip").build())
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
                client.newCall(Request.Builder().url("$backUrl/users/$userId/nextTrip").build())
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