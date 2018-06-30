package mobile.frba.utn.tpmobile.models

import android.arch.persistence.room.Entity
import com.google.android.gms.maps.model.LatLng
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
import java.util.*

/**
 * Created by Gustavo on 5/6/18.
 */
@Entity(tableName = "text_table")
data class Text(var text: String, override var mg:Int, override var title:String, @Ignore override var date: DateTime, var dateForDB: Date, override var geoLocation: LatLng?, @field:PrimaryKey override var id : Int?, override  var userId : String?, override var tripId : Int?) : Event(EventType.TEXT,mg,geoLocation,id,userId,tripId,date, title) {
    @JvmOverloads constructor(text: String, mg:Int, title:String, date: DateTime, geoLocation: LatLng?,id : Int?,userId : String?, tripId : Int?)
            : this(text, mg, title, date, date.toDate(),geoLocation,id ,userId, tripId )
}