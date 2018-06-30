package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng
import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */
data class Video(var description: String,override var mg:Int, override var title : String, override var date : DateTime, var url: String, override var geoLocation: LatLng?, override var id : Int?, override var userId : String?, override var tripId : Int?) : Event(EventType.VIDEO, mg,geoLocation,id, userId, tripId,date, title)

