package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng
import mobile.frba.utn.tpmobile.activities.DateFormatter
import org.joda.time.DateTime
import org.json.JSONObject
import java.io.Serializable
import android.arch.persistence.room.*
import java.util.*

/**
 * Created by Gustavo on 5/6/18.
 */
abstract class Event(var eventType: EventType, open var likes: HashSet<String>, open var geoLocation: Coordinate?,open var id : Int?,open var userId : String?, open var tripId : Int?,  open var date : DateTime, var dateDb : Long, open var title:String) :Serializable {

    fun urlUserId(): String {
       return userId!!.replace(" ","%20")
    }
}

fun getEventFromJson (jsonObject: JSONObject, fromRoomDB : Boolean = false ) : Event {
    val eventType : EventType = EventType.valueOf(jsonObject.getString("eventType"))
    val jsonGeoLocation = jsonObject.getJSONObject("geoLocation")
    val geoLocation = Coordinate(jsonGeoLocation.getDouble("latitude"),jsonGeoLocation.getDouble("longitude"))
    val id = jsonObject.getInt("id")
    val userId = jsonObject.getString("userId")
    val tripId = jsonObject.getInt("tripId")
    val likesJson = jsonObject.getJSONArray("likes")
    var x = 0
    val date : DateTime
    if (fromRoomDB) {
        val timestamp = jsonObject.getLong("dateDb")
        val juDate = Date(timestamp)
        date = DateTime(juDate)

    } else {
        date  = DateFormatter.getDateTimeFromString( jsonObject.getString("date"))
    }
    val likes : HashSet<String> = emptyArray<String>().toHashSet()
    while (x < likesJson.length()) {
        val like = likesJson.getString(x)
        likes.add(like)
        x++
    }
    val event =   when (eventType){
        EventType.PHOTO -> Photo(jsonObject.getString("url"),likes,jsonObject.getString("title"), date ,jsonObject.getString("description"),geoLocation,id,userId,tripId)
        EventType.TEXT -> Text(jsonObject.getString("text"),likes,jsonObject.getString("title"), date,geoLocation,id,userId,tripId)
        EventType.VIDEO -> Video(jsonObject.getString("description"),likes,jsonObject.getString("title"),date,jsonObject.getString("url"),geoLocation,id,userId,tripId)
    }
    return event
}

fun getLikeFromJson(jsonObject: JSONObject) : String {
    return jsonObject.getString("userId")
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

class Like(userId: String)