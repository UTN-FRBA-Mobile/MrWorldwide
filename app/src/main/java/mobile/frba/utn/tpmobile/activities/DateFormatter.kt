package mobile.frba.utn.tpmobile.activities

import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

class DateFormatter{
    companion object {
        val SIMPLE_DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        fun format(date: DateTime): String = SIMPLE_DATE_FORMAT.format(date.toDate())

        fun getDateTimeFromString(date : String) : DateTime = DateTime(SIMPLE_DATE_FORMAT.parse(date))

        fun getDateTimeFromStringWithSlash(date : String) :DateTime = DateTime(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date))
    }
}