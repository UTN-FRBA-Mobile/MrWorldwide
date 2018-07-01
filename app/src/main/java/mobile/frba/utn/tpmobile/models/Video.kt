package mobile.frba.utn.tpmobile.models

import android.arch.persistence.room.*
import org.joda.time.DateTime
import android.arch.persistence.room.*

/**
 * Created by Gustavo on 5/6/18.
 */
@Entity(tableName = "video_table")
data class Video(var description: String,@ColumnInfo(name = "video_likes") override var likes: HashSet<String>, @ColumnInfo(name = "video_title") override var title : String, @Ignore override var date : DateTime, var url: String, @ColumnInfo(name = "video_loc") override var geoLocation: Coordinate?, @ColumnInfo(name = "video_id") @field:PrimaryKey override var id : Int?, @ColumnInfo(name = "video_usrId") override var userId : String?, @ColumnInfo(name = "video_tripId") override var tripId : Int?) : Event(EventType.VIDEO, likes, geoLocation,id, userId, tripId,date, date!!.toDate().time, title)
