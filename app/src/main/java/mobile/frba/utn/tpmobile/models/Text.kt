package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng
import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */
open class Text(var text: String, var date:DateTime, var title:String, override var geoLocation: LatLng?) : Event(EventType.TEXT,geoLocation) {
}