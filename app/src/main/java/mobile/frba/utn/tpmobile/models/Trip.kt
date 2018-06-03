package mobile.frba.utn.tpmobile.models

import android.os.Build
import android.support.annotation.RequiresApi
import mobile.frba.utn.tpmobile.activities.DateFormatter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import org.json.JSONObject

/**
 * Created by Gustavo on 5/6/18.
 */

open class Trip(var id : Int,var title: String, var tripPhoto: TripPhoto, var startDate: DateTime, var finishDate: DateTime, var events: MutableList<Event>){
     companion object {

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
                     events
             )
         }
     }
}