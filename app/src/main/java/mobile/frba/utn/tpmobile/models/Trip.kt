package mobile.frba.utn.tpmobile.models

import android.arch.persistence.room.*
import android.os.Build
import android.support.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import mobile.frba.utn.tpmobile.activities.DateFormatter
import org.joda.time.DateTime
import org.json.JSONObject
import java.lang.reflect.Type
import java.io.Serializable
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.internal.LinkedTreeMap
import org.json.JSONArray
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Gustavo on 5/6/18.
 */


@Entity(tableName = "trip_table")
data class Trip(@field:PrimaryKey var id : Int?, var title: String, var tripPhoto: TripPhoto, @Ignore var startDate: DateTime, var startDateForDB: Date, @Ignore var finishDate: DateTime, var endDateForDB: Date, @Ignore var events: MutableList<Event>, var eventsString: String?) : Serializable {
    @JvmOverloads constructor(id: Int?, title: String, tripPhoto: TripPhoto, startDate: DateTime, finishDate: DateTime, events: MutableList<Event>)
            : this(id, title, tripPhoto, startDate, startDate.toDate() ,finishDate, finishDate.toDate(), events, Trip.EventListToString(events))

    constructor() : this(-1, "",
            TripPhoto("", DateTime()), DateTime(), DateTime(),
            ArrayList<Event>()
    )



    companion object {

        fun EventListToString(events: MutableList<Event>): String {
            val gson = GsonBuilder().setExclusionStrategies(SuperclassExclusionStrategy(), CustomExclusionStrategies()).create()
            var jsonEvents : String = gson.toJson(events)
            return jsonEvents
        }

        fun getFromString(string: String): Trip? {
            if (string.isEmpty()) {
                return null
            }
            return Trip.getFromJson(JSONObject(string))
        }

        fun getFromJson(jsonObject: JSONObject): Trip {
            val tripPhoto = jsonObject.getJSONObject("tripPhoto")
            val eventsJson = jsonObject.getJSONArray("events")
            var x = 0
            val events: MutableList<Event> = emptyArray<Event>().toMutableList()
            while (x < eventsJson.length()) {
                val event = eventsJson.getJSONObject(x)
                events.add(getEventFromJson(event))
                x++
            }

            val fTrip =  Trip(jsonObject.getInt("id"),
                    jsonObject.getString("title"),
                    TripPhoto(tripPhoto.getString("url"), DateFormatter.getDateTimeFromString(tripPhoto.getString("date"))),
                    DateFormatter.getDateTimeFromString(jsonObject.getString("startDate")),
                    DateFormatter.getDateTimeFromString(jsonObject.getString("finishDate")),
                    events
            )
            return fTrip
        }


        fun ListStringToEventList(stringEvents: String): List<Event> {
            if (stringEvents.length == 0)
                return ArrayList()
            var founderListType: Type = object : TypeToken<MutableList<Any>>() {}.getType()
            var events: MutableList<Any> = Gson().fromJson(stringEvents, founderListType)
            var finalEvents: MutableList<Event> = emptyArray<Event>().toMutableList()


            for (event in events) {
                val jsonObj : JSONObject = JSONObject()
                for (entry in (event as LinkedTreeMap<*,*>).entries) {
                    val key = entry.key as String
                    val value =   when (key){
                        "geoLocation" -> JSONObject().put("latitude", (entry.value as LinkedTreeMap<*,*>).entries.elementAt(0).value).put("longitude", (entry.value as LinkedTreeMap<*,*>).entries.elementAt(1).value)
                        "id" -> (entry.value as Double).toDouble()
                        "tripId" -> (entry.value as Double).toDouble()
                        "dateDb " -> (entry.value as Double).toDouble()
                        "likes" -> {
                            val jsonRarr = JSONArray()
                            for (any in (entry.value as java.util.ArrayList<*>).toArray()) {
                                jsonRarr.put(any)
                            }
                            jsonRarr
                        }
                        else -> {
                            if (key.equals("dateDb"))
                                (entry.value as Double).toDouble()
                            else
                              entry.value as String
                        }
                    }
                    jsonObj.put(key, value)
                }
                finalEvents.add(getEventFromJson(jsonObj, fromRoomDB = true))
            }
            return finalEvents
        }

    }
}