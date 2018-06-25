package mobile.frba.utn.tpmobile.singletons

import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import com.google.android.gms.maps.model.LatLng
import mobile.frba.utn.tpmobile.daos.TripsRepository
import mobile.frba.utn.tpmobile.models.*
import mobile.frba.utn.tpmobile.services.RestClient

import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import mobile.frba.utn.tpmobile.Constants
import mobile.frba.utn.tpmobile.activities.DateFormatter
import mobile.frba.utn.tpmobile.models.Event
import mobile.frba.utn.tpmobile.models.Trip
import mobile.frba.utn.tpmobile.models.getEventFromJson
import okhttp3.*
import org.joda.time.DateTime
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

object RepoTrips {
     var trips: MutableList<Trip> = ArrayList()
     var backUrl = "http://192.168.0.27:3000"
     val client = OkHttpClient()
    var userId = "Agustin Vertebrado"
    val gson = GsonBuilder()
            .registerTypeAdapter(DateTime::class.java, JsonSerializer<DateTime> { date, _, _ -> JsonPrimitive(DateFormatter.format(date)) })
            .create()

    private val tripsLocalRepository: TripsRepository

    init {
        tripsLocalRepository = TripsRepository(AppHolder.getApp())
    }

    fun getLocalTrips(lca: Fragment, callback: (MutableList<Trip>)-> Unit): Unit{
        tripsLocalRepository.getAllTrips().observe(lca, Observer { trips ->
            if (trips != null) {
                trips.forEach {
                    tr ->
                    tr.events = Trip.ListStringToEventList(tr.eventsString!!).toMutableList()
                }
                callback.invoke(RepoTrips.trips)
            }
        })
    }

    fun getTrips(lca: Fragment) : ((MutableList<Trip>)-> Unit)->Unit{
        return { callback ->
            if (!RestClient.isOnline()){
                getLocalTrips(lca,callback)
            } else {
                run {
                    client.newCall(Request.Builder().url(okhttp3.HttpUrl.parse("$backUrl/users/$userId/trips")).build())
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
                                        val fTrip = Trip.getFromJson(trip)
                                        tripsLocalRepository.insert(fTrip)
                                        trips.add(fTrip)
                                        x++
                                    }
                                    callback.invoke(trips)
                                }
                            })
                }
            }
        }
    }

    fun savePhotoAndThenAddTrip(photo: ByteArray,trip: Trip, callback: () -> Unit) {
        "https://api.imgur.com/3/image"
                .httpPost()
                .header(Pair("Authorization", Constants.clientAuth))
                .body(photo)
                .responseJson({_,response, result ->
                    trip.tripPhoto.url = JSONObject((result as Result.Success).value.content).getJSONObject("data").getString("link")
                    addTrip(trip, callback)
                })

    }

    fun addTrip(trip: Trip, callback: () -> Unit) {
        trips.add(trips.lastIndex + 1, trip)
        "$backUrl/trips/$userId".httpPost().body(gson.toJson(trip)).header(Pair("Content-Type", "application/json")).response({ _, _, result ->
            println(result)
            callback.invoke()
        })
    }

    fun getLocalTrip(id: Int, lca: Fragment, callback: (Trip)-> Unit): Unit{
        tripsLocalRepository.getTrip(id).observe(lca, Observer { trip ->
            if (trip != null) {
                trip.events = Trip.ListStringToEventList(trip.eventsString!!).toMutableList()
                callback.invoke(trip)
            }
        })
    }

    fun getTrip(lca: Fragment, id: Int): ((Trip?)-> Unit)->Unit{
        return { callback ->
            if (!RestClient.isOnline()){
                getLocalTrip(id, lca,callback)
            } else {
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
    }

    fun getActualTripFor(): ((Trip?) -> Unit) -> Unit {
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

    fun getNextTripFor(): ((Trip?) -> Unit) -> Unit {
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

    fun getFriendEvents(): ((MutableList<Event>)-> Unit)->Unit {
        return { callback ->
            run {
                client.newCall(Request.Builder().url("$backUrl/users/${RepoTrips.userId}/friendEvents").build())
                        .enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                throw Error("rompio todo!")
                            }

                            override fun onResponse(call: Call, response: Response) {
                                val jsonEvents = JSONArray(response.body()!!.string())
                                var x = 0
                                val events: MutableList<Event> = emptyArray<Event>().toMutableList()
                                while (x < jsonEvents.length()) {
                                    val event = jsonEvents.getJSONObject(x)
                                    events.add(getEventFromJson(event))
                                    x++
                                }
                                callback.invoke(events)
                            }
                        })
            }
        }
    }
}