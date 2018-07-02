package mobile.frba.utn.tpmobile.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.*

import org.joda.time.DateTime
import java.util.*

/**
 * Created by Gustavo on 5/6/18.
 */
@Entity(tableName = "text_table")
data class Text(var text: String, @ColumnInfo(name = "text_likes") override var likes: HashSet<String>,  @ColumnInfo(name = "text_title") override var title:String, @Ignore override var date: DateTime, @ColumnInfo(name = "text_loc") override var geoLocation: Coordinate?, @ColumnInfo(name = "text_id") @field:PrimaryKey override var id : Int?, @ColumnInfo(name = "text_usrId") override  var userId : String?, @ColumnInfo(name = "text_tripId") override var tripId : Int?) : Event(EventType.TEXT,likes,geoLocation,id,userId,tripId,date, date!!.toDate().time, title)
