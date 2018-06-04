package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng
import mobile.frba.utn.tpmobile.activities.DateFormatter
import org.json.JSONObject

/**
 * Created by Gustavo on 5/6/18.
 */
open abstract class Event(val eventType: EventType, open var geoLocation: LatLng?) {

}

fun getEventFromJson (jsonObject: JSONObject) : Event {
    val eventType : EventType = EventType.valueOf(jsonObject.getString("eventType"))
    val jsonGeoLocation = jsonObject.getJSONObject("geoLocation")
    val geoLocation = LatLng(jsonGeoLocation.getDouble("x"),jsonGeoLocation.getDouble("y"))
    return  when (eventType){
        EventType.PHOTO -> Photo(jsonObject.getString("url"), DateFormatter.getDateTimeFromString( jsonObject.getString("date")),jsonObject.getString("description"),geoLocation)
        EventType.TEXT -> Text(jsonObject.getString("text"), DateFormatter.getDateTimeFromString(jsonObject.getString("date")),jsonObject.getString("title"),geoLocation)
        EventType.VIDEO -> Video(jsonObject.getString("description"),jsonObject.getString("url"),geoLocation)
    }
}
enum class EventType(val viewType: Int) {
    PHOTO(1),
    TEXT(0),
    VIDEO(2);
}