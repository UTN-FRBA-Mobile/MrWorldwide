package mobile.frba.utn.tpmobile.models

import java.util.*

/**
 * Created by Gustavo on 5/6/18.
 */
open class Trip {
    val title: String = ""
    val tripPhoto: Photo? = null
    val date: List<Date> = emptyList()
    val events: List<Event> = emptyList()
}