package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng
import mobile.frba.utn.tpmobile.activities.DateFormatter
import org.joda.time.DateTime
import org.json.JSONObject
import java.io.Serializable


/**
 * Created by Gustavo on 5/6/18.
 */
abstract class Event(val eventType: EventType, val mg: Int, open var geoLocation: Coordinate?, open var id : Int?, open var userId : String?, open var tripId : Int?, open var date : DateTime, open var title:String) : Serializable {
    fun urlUserId(): String {
       return userId!!.replace(" ","%20")
    }
}

fun getEventFromJson (jsonObject: JSONObject) : Event {
    val eventType : EventType = EventType.valueOf(jsonObject.getString("eventType"))
    val jsonGeoLocation = jsonObject.getJSONObject("geoLocation")
    val geoLocation = Coordinate(jsonGeoLocation.getDouble("latitude"),jsonGeoLocation.getDouble("longitude"))
    val id = jsonObject.getInt("id")
    val userId = jsonObject.getString("userId")
    val tripId = jsonObject.getInt("tripId")
    val mg = jsonObject.getInt("mg")
    return  when (eventType){
        EventType.PHOTO -> Photo(jsonObject.getString("url"),mg,jsonObject.getString("title"), DateFormatter.getDateTimeFromString( jsonObject.getString("date")),jsonObject.getString("description"),geoLocation,id,userId,tripId)
        EventType.TEXT -> Text(jsonObject.getString("text"),mg,jsonObject.getString("title"), DateFormatter.getDateTimeFromString(jsonObject.getString("date")),geoLocation,id,userId,tripId)
        EventType.VIDEO -> Video(jsonObject.getString("description"),mg,jsonObject.getString("title"),DateFormatter.getDateTimeFromString(jsonObject.getString("date")),jsonObject.getString("url"),geoLocation,id,userId,tripId)
    }
}
enum class EventType(val viewType: Int) : Serializable {
    PHOTO(1),
    TEXT(0),
    VIDEO(2);
}

class Coordinate (var latitude: Double, var longitude: Double) : Serializable {
    fun toLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }

    companion object {
        fun fromLatLng(location: LatLng): Coordinate {
            return Coordinate(location.latitude, location.longitude)
        }
    }
}