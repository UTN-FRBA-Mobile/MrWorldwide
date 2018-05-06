package mobile.frba.utn.tpmobile.models

import java.util.*

/**
 * Created by Gustavo on 5/6/18.
 */
open class Trip(var title: String, var tripPhoto: Photo, var startDate: Date, var finishDate: Date, var events: List<Event>) {
}