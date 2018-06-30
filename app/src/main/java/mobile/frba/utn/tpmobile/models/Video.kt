package mobile.frba.utn.tpmobile.models

import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */
data class Video(var description: String,override var likes: HashSet<String>, override var title : String, override var date : DateTime, var url: String, override var geoLocation: Coordinate?, override var id : Int?, override var userId : String?, override var tripId : Int?) : Event(EventType.VIDEO, likes, geoLocation,id, userId, tripId,date, title)
