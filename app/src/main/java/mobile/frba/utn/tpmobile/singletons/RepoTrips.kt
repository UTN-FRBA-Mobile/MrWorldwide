package mobile.frba.utn.tpmobile.singletons

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.facebook.FacebookSdk.getApplicationContext
import com.google.android.gms.maps.model.LatLng
import mobile.frba.utn.tpmobile.daos.TripsRepository
import mobile.frba.utn.tpmobile.models.*
import mobile.frba.utn.tpmobile.services.RestClient

import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import mobile.frba.utn.tpmobile.Constants
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.activities.DateFormatter
import mobile.frba.utn.tpmobile.models.Event
import mobile.frba.utn.tpmobile.models.Trip
import mobile.frba.utn.tpmobile.models.getEventFromJson
import okhttp3.*
import org.joda.time.DateTime
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException

object RepoTrips {
     var trips: MutableList<Trip> = ArrayList()
    var prefs: SharedPreferences? = null
    var backUrl = "http://mrworldwide.herokuapp.com"
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
                callback.invoke(trips.toMutableList())
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
                                        //tripsLocalRepository.insert(fTrip)
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

    fun getTripsWithEvents(lca: Fragment): ((MutableList<Trip>) -> Unit) -> Unit {
        return { callback ->
            if (!RestClient.isOnline()){
                getLocalTrips(lca,callback)
            } else {
                run {
                    client.newCall(Request.Builder().url("$backUrl/users/$userId/tripsWithEvents").build())
                            .enqueue(object : Callback {
                                override fun onFailure(call: Call, e: IOException) {
                                    throw Error("rompio todo!")
                                }

                                override fun onResponse(call: Call, response: Response) {
                                    val jsonTrips = JSONArray(response.body()!!.string())

                                    var x = 0
                                    val trips: MutableList<Trip> = emptyArray<Trip>().toMutableList()
                                    while (x < jsonTrips.length()) {
                                        val jtrip = jsonTrips.getJSONObject(x)
                                        val trip = Trip.getFromJson(jtrip)
                                        trips.add(trip)
                                        tripsLocalRepository.insert(trip)
                                        x++
                                    }
                                    callback.invoke(trips)
                                }
                            })
                }
            }
        }
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

    fun saveActualTripIdToPrefs(tripId : String){
        val editor = prefs!!.edit()
        editor.putString("actualTripId", tripId)
        editor.apply()
    }

    fun saveNextTripIdToPrefs(tripId : String){
        val editor = prefs!!.edit()
        editor.putString("nextTripId", tripId)
        editor.apply()
    }

    fun getNextTripIdFromPrefs(): Int{
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        return prefs!!.getString("nextTripId", "notFound").toInt()
    }

    fun getActualTripIdFromPrefs(): Int{
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        return prefs!!.getString("actualTripId", "notFound").toInt()
    }

    fun getActualTripFor(lca: Fragment): ((Trip?) -> Unit) -> Unit {
        return { callback ->
            if (!RestClient.isOnline()){
                try {
                    getLocalTrip(getActualTripIdFromPrefs(), lca,callback)
                } catch (e: Exception){
                    callback.invoke(null)
                }
            } else {
                run {
                    client.newCall(Request.Builder().url("$backUrl/users/$userId/actualTrip").build())
                            .enqueue(object : Callback {
                                override fun onFailure(call: Call, e: IOException) {
                                    throw Error("rompio todo!")
                                }
                                override fun onResponse(call: Call, response: Response) {
                                    try {
                                        val trip = Trip.getFromString(response.body()!!.string())
                                        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                                        saveActualTripIdToPrefs(trip?.id.toString())
                                        callback.invoke(trip)
                                    } catch (e: Exception){
                                        callback.invoke(null)
                                    }
                                }
                            })
                }
            }
        }

    }

    fun getNextTripFor(lca: Fragment): ((Trip?) -> Unit) -> Unit {
        return { callback ->
            if (!RestClient.isOnline()){
                try {
                    getLocalTrip(getNextTripIdFromPrefs(), lca,callback)
                } catch (e: Exception){
                    callback.invoke(null)
                }
            } else {
                run {
                    client.newCall(Request.Builder().url("$backUrl/users/$userId/nextTrip").build())
                            .enqueue(object : Callback {
                                override fun onFailure(call: Call, e: IOException) {
                                    throw Error("rompio todo!")
                                }

                                override fun onResponse(call: Call, response: Response) {
                                    val trip = Trip.getFromString(response.body()!!.string())
                                    saveNextTripIdToPrefs(trip?.id.toString())
                                    callback.invoke(trip)
                                }
                            })
                }
            }
        }
    }

    fun getLocalFriendEvents(lca: Fragment, callback: (MutableList<Event>)-> Unit): Unit{
        tripsLocalRepository.getFriendsEvents(lca, callback)
    }

    fun getFriendEvents(lca: Fragment): ((MutableList<Event>)-> Unit)->Unit {
        return { callback ->
            if (!RestClient.isOnline()){
                getLocalFriendEvents(lca, callback)
            } else {
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
                                        val jEvent = jsonEvents.getJSONObject(x)
                                        val ev = getEventFromJson(jEvent)
                                        events.add(ev)
                                        tripsLocalRepository.insert(ev)
                                        x++
                                    }
                                    callback.invoke(events)
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

    fun savePhotoThenUpdateTrip(photo: ByteArray,trip: Trip, callback: () -> Unit) {
        "https://api.imgur.com/3/image"
                .httpPost()
                .header(Pair("Authorization", Constants.clientAuth))
                .body(photo)
                .responseJson({_,response, result ->
                    trip.tripPhoto.url = JSONObject((result as Result.Success).value.content).getJSONObject("data").getString("link")
                    updateTrip(trip, callback)
                })
    }

    fun updateTrip(trip: Trip, callback: () -> Unit) {
        trips.add(trips.lastIndex + 1, trip)
        "$backUrl/trips/${trip.id}"
                .httpPut()
                .body(gson.toJson(trip))
                .header(Pair("Content-Type", "application/json"))
                .response({ _, _, result ->
                    println(result)
                    callback.invoke()
                })
    }

    fun deleteTrip(trip: Trip, callback: () -> Unit) {
        trips.remove(trip)
        "$backUrl/trips/${trip.id}"
            .httpDelete()
            .response({ _, _, result ->
                println(result)
                callback.invoke()
            })
    }
}
