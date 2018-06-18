package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng
import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */
open class Photo(var url: String,override var date: DateTime, var description: String, override var geoLocation: LatLng?, override var id : Int, override var userId : String, override var tripId : Int) : Event(EventType.PHOTO, geoLocation,id, userId, tripId,date) {
}