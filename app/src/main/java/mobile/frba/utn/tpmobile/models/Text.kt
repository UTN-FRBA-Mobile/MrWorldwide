package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng
import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */
open class Text(var text: String, override var date:DateTime, var title:String, override var geoLocation: LatLng?,override var id : Int?, override  var userId : String?, override var tripId : Int?) : Event(EventType.TEXT,geoLocation,id,userId,tripId,date) {
}