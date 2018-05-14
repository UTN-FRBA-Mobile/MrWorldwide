package mobile.frba.utn.tpmobile.models

import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */
open class TripPhoto(var url: String, var date: DateTime) : Event {
    override val viewType=1
}