package mobile.frba.utn.tpmobile.models

import android.arch.persistence.room.*
import android.os.Build
import android.support.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mobile.frba.utn.tpmobile.activities.DateFormatter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import org.json.JSONObject
import java.lang.reflect.Type

/**
 * Created by Gustavo on 5/6/18.
 */


@Entity(tableName = "trip_table")
data class Trip(@field:PrimaryKey var id : Int, var title: String, var tripPhoto: TripPhoto, var startDate: DateTime, var finishDate: DateTime, @Ignore var events: MutableList<Event>, var eventsString: String?){
    constructor() : this(-1, "",
            TripPhoto("", DateTime() ), DateTime(), DateTime(),
            ArrayList(), ""
    )
    @JvmOverloads constructor(id : Int, title: String, tripPhoto: TripPhoto, startDate: DateTime, finishDate: DateTime, events: MutableList<Event>)
            : this(id, title, tripPhoto, startDate, finishDate, events, Trip.EventListToString(events))

    companion object {

        fun EventListToString(events: List<Event>): String {
            return Gson().toJson(events)
        }

        fun ListStringToEventList(stringEvents: String): List<Event> {
            if (stringEvents.length == 0)
                return ArrayList()
            var founderListType : Type = object: TypeToken<ArrayList<Event>>() {}.getType()
            var events : List<Event>  = Gson().fromJson(stringEvents, founderListType)
            return events
        }

         fun getFromString (string:String): Trip? {
            if(string.isEmpty()){
                return null
            }
            return getFromJson(JSONObject(string))
         }

         fun getFromJson (jsonObject:JSONObject): Trip {
             val tripPhoto = jsonObject.getJSONObject("tripPhoto")
             val eventsJson = jsonObject.getJSONArray("events")
             var x = 0
             val events : MutableList<Event> = emptyArray<Event>().toMutableList()
             while (x < eventsJson.length()) {
                 val event = eventsJson.getJSONObject(x)
                 events.add(getEventFromJson(event))
                 x++
             }

             return Trip(jsonObject.getInt("id"),
                     jsonObject.getString("title"),
                     TripPhoto(tripPhoto.getString("url"), DateFormatter.getDateTimeFromString(tripPhoto.getString("date"))),
                     DateFormatter.getDateTimeFromString(jsonObject.getString("startDate")),
                     DateFormatter.getDateTimeFromString(jsonObject.getString("finishDate")),
                     events,""
             )
         }
     }
}