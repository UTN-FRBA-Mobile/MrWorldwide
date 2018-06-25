package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng
import org.joda.time.DateTime
import java.io.Serializable

/**
 * Created by Gustavo on 5/6/18.
 */
open class TripPhoto(var url: String, var date: DateTime)  : Serializable { }