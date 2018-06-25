package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng
import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */
open class Photo(var url: String,mg: Int,title: String, date: DateTime, var description: String, geoLocation: LatLng?, id : Int?, userId : String?, tripId : Int?) : Event(EventType.PHOTO,mg, geoLocation,id, userId, tripId,date,title) {
}