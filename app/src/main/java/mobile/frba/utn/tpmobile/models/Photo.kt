package mobile.frba.utn.tpmobile.models

import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */
open class Photo(var url: String, var date: DateTime, var description: String) : Event(EventType.PHOTO) {
}