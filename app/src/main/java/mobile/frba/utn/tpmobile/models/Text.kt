package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng
import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */
open class Text(var text: String, date:DateTime, title: String, geoLocation: LatLng?, id : Int?,  userId : String?, tripId : Int?) : Event(EventType.TEXT,geoLocation,id,userId,tripId,date,title) {
}