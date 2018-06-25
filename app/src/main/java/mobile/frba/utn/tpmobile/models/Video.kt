package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng
import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */
open class Video(var description: String,mg:Int,title : String, date : DateTime, var url: String, geoLocation: Coordinate?, id : Int?, userId : String?,  tripId : Int?) : Event(EventType.VIDEO,mg, geoLocation,id, userId, tripId,date,title)