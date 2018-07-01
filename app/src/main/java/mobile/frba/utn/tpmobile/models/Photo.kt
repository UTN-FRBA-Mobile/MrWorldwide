package mobile.frba.utn.tpmobile.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.*
import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */

@Entity(tableName = "photo_table")
data class Photo(var url: String,@ColumnInfo(name = "photo_likes") override var likes: HashSet<String>, @ColumnInfo(name = "photo_title") override var title: String, @Ignore override var date: DateTime, var description: String, @ColumnInfo(name = "photo_loc") override var geoLocation: Coordinate?, @ColumnInfo(name = "photo_id") @field:PrimaryKey override var id : Int?, @ColumnInfo(name = "photo_usrId") override var userId : String?, @ColumnInfo(name = "photo_tripId") override var tripId : Int?) : Event(EventType.PHOTO,likes, geoLocation,id, userId, tripId,date, date!!.toDate().time, title) {

}