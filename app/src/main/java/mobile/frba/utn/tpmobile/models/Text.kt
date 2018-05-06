package mobile.frba.utn.tpmobile.models

import java.time.LocalDate

/**
 * Created by Gustavo on 5/6/18.
 */
open class Text(var text: String, var date:LocalDate, var title:String) : Event {
    override val viewType=0
}