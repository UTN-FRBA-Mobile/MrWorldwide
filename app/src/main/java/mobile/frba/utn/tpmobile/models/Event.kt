package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng
import mobile.frba.utn.tpmobile.activities.DateFormatter
import org.joda.time.DateTime
import org.json.JSONObject
import java.time.LocalDate

/**
 * Created by Gustavo on 5/6/18.
 */
abstract class Event(val eventType: EventType, open var geoLocation: LatLng?,open var id : Int?,open var userId : String?, open var tripId : Int?, open var date : DateTime, open var title:String) {
    fun urlUserId(): String {
       return userId!!.replace(" ","%20")
    }
}

fun getEventFromJson (jsonObject: JSONObject) : Event {
    val eventType : EventType = EventType.valueOf(jsonObject.getString("eventType"))
    val jsonGeoLocation = jsonObject.getJSONObject("geoLocation")
    val geoLocation = LatLng(jsonGeoLocation.getDouble("latitude"),jsonGeoLocation.getDouble("longitude"))
    val id = jsonObject.getInt("id")
    val userId = jsonObject.getString("userId")
    val tripId = jsonObject.getInt("tripId")
    return  when (eventType){
        EventType.PHOTO -> Photo(jsonObject.getString("url"),jsonObject.getString("title"), DateFormatter.getDateTimeFromString(jsonObject.getString("date")),jsonObject.getString("description"),geoLocation,id,userId,tripId)
        EventType.TEXT -> Text(jsonObject.getString("text"), DateFormatter.getDateTimeFromString(jsonObject.getString("date")),jsonObject.getString("title"),geoLocation,id,userId,tripId)
        EventType.VIDEO -> Video(jsonObject.getString("description"),jsonObject.getString("title"),DateFormatter.getDateTimeFromString(jsonObject.getString("date")),jsonObject.getString("url"),geoLocation,id,userId,tripId)
    }
}
enum class EventType(val viewType: Int) {
    PHOTO(1),
    TEXT(0),
    VIDEO(2);
}