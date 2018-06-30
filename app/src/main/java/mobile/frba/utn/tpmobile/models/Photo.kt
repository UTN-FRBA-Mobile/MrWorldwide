package mobile.frba.utn.tpmobile.models

import android.arch.persistence.room.Entity
import com.google.android.gms.maps.model.LatLng
import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */

@Entity(tableName = "photo_table")
data class Photo(var url: String,override var likes: HashSet<String>,override var title: String, override var date: DateTime, var description: String, override var geoLocation: Coordinate?, override var id : Int?, override var userId : String?, override var tripId : Int?) : Event(EventType.PHOTO,likes, geoLocation,id, userId, tripId,date, title) {

}