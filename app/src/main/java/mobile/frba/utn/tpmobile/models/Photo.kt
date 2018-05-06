package mobile.frba.utn.tpmobile.models

import java.time.LocalDate

/**
 * Created by Gustavo on 5/6/18.
 */
open class Photo(var url: String, var date: LocalDate) : Event {
    override val viewType=1
}