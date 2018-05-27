package mobile.frba.utn.tpmobile.models

import mobile.frba.utn.tpmobile.activities.DateFormatter
import org.joda.time.DateTime
import org.json.JSONObject

/**
 * Created by Gustavo on 5/6/18.
 */
open abstract class Event(val eventType: EventType) {

}

fun getEventFromJson (jsonObject: JSONObject) : Event {
    val eventType : EventType = EventType.valueOf(jsonObject.getString("eventType"))
    return  when (eventType){
        EventType.PHOTO -> Photo(jsonObject.getString("url"), DateFormatter.getDateTimeFromString( jsonObject.getString("date")),jsonObject.getString("description"))
        EventType.TEXT -> Text(jsonObject.getString("text"), DateFormatter.getDateTimeFromString(jsonObject.getString("date")),jsonObject.getString("title"))
        EventType.VIDEO -> Video(jsonObject.getString("description"),jsonObject.getString("url"))
    }
}
enum class EventType(val viewType: Int) {
    PHOTO(1),
    TEXT(0),
    VIDEO(2);
}