package mobile.frba.utn.tpmobile.daos

import android.arch.persistence.room.TypeConverter
import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mobile.frba.utn.tpmobile.models.Event
import mobile.frba.utn.tpmobile.models.TripPhoto
import org.joda.time.DateTime
import java.lang.reflect.Type
import com.google.gson.GsonBuilder
import com.fatboyindustrial.gsonjodatime.Converters.registerDateTime
import mobile.frba.utn.tpmobile.models.Coordinate
import mobile.frba.utn.tpmobile.models.EventType
import java.util.*




class WorldConverters {

    @TypeConverter
    fun tripPhotoToJson(tripPhoto: TripPhoto): String {
        val dateNew = tripPhoto.date!!.toDate()
        val dbTripPhoto = TripPhoto(tripPhoto.url, null, dateNew)
        return Gson().toJson(dbTripPhoto)
    }

    @TypeConverter
    fun jsonToTripPhoto(tripPhotoJson: String): TripPhoto {
        val dbTripPhoto = Gson().fromJson(tripPhotoJson, TripPhoto::class.java)
        return TripPhoto(dbTripPhoto.url, DateTime(dbTripPhoto.dateForDB))
    }

    @TypeConverter
    fun fromTimestamp(value: String?): DateTime? {
        val date = Gson().fromJson(value, Date::class.java)
        val dateTime = DateTime(date)
        return dateTime
    }

    @TypeConverter
    fun dateToTimestamp(date: DateTime?): String? {
        val dateNew = date!!.toDate()
        return Gson().toJson(dateNew)
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun setToString(likes: HashSet<String>): String {
        return likes.toString()
    }

    @TypeConverter
    fun stringToSet(likesString: String): HashSet<String> {
        val likes : HashSet<String> = HashSet<String>()
        likesString.split(",").forEach{
            like ->
            likes.add(like)
        }
        return likes
    }

    @TypeConverter
    fun geolocToString(latlong: Coordinate): String {
        return latlong.latitude.toString() + "--" + latlong.longitude.toString()
    }

    @TypeConverter
    fun stringToGeoloc(stringLatlong: String): Coordinate {
        val parts: List<String> =  stringLatlong.split("--")
        return Coordinate(parts.get(0).toDouble(), parts.get(1).toDouble())
    }

    @TypeConverter
    fun toEventType(ordinal: Int): EventType {
        return EventType.values()[ordinal]
    }

    @TypeConverter
    fun toOrdinal(eventType: EventType): Int {
        return eventType.ordinal
    }

}
