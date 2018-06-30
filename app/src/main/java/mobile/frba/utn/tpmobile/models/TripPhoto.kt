package mobile.frba.utn.tpmobile.models

import android.arch.persistence.room.Ignore
import org.joda.time.DateTime
import java.util.*
import java.io.Serializable

/**
 * Created by Gustavo on 5/6/18.
 */
data class TripPhoto(var url: String, @Ignore var date: DateTime?, var dateForDB: Date) : Serializable   {
    @JvmOverloads constructor(url: String, date: DateTime)
            : this(url, date, date.toDate())
}

