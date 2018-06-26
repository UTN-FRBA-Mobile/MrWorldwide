package mobile.frba.utn.tpmobile.models

import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */
open class Video(var description: String, likes: HashSet<String>,title : String, date : DateTime, var url: String, geoLocation: Coordinate?, id : Int?, userId : String?,  tripId : Int?) : Event(EventType.VIDEO, likes, geoLocation,id, userId, tripId,date,title)