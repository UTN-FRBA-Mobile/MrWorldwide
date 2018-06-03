package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng
import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */
open class Photo(var url: String, var date: DateTime, var description: String, override var geoLocation: LatLng?) : Event(EventType.PHOTO, geoLocation) {
}