package mobile.frba.utn.tpmobile.models

import org.joda.time.DateTime

/**
 * Created by Gustavo on 5/6/18.
 */
open class Trip(var title: String, var tripPhoto: TripPhoto, var startDate: DateTime, var finishDate: DateTime, var events: List<Event>)