package mobile.frba.utn.tpmobile.models

import org.joda.time.DateTime
import org.json.JSONObject

/**
 * Created by Gustavo on 5/6/18.
 */

open class Trip(var id : Int,var title: String, var tripPhoto: TripPhoto, var startDate: DateTime, var finishDate: DateTime, var events: MutableList<Event>){
     companion object {
         fun getFromJson (jsonObject:JSONObject): Trip {
             val tripPhoto = jsonObject.getJSONObject("tripPhoto")
             val eventsJson = jsonObject.getJSONArray("events")
             val x = 0
             val events : MutableList<Event> = emptyArray<Event>().toMutableList()
             while (x < eventsJson.length()) {
                 val event = eventsJson.getJSONObject(x)
                 events.add(getEventFromJson(event))
             }

             return Trip(jsonObject.getInt("id"),
                     jsonObject.getString("title"),
                     TripPhoto(tripPhoto.getString("url"), DateTime(tripPhoto.getString("date"))),
                     DateTime(jsonObject.getString("startDate")),
                     DateTime(jsonObject.getString("finishDate")),
                     events
             )
         }
     }
}