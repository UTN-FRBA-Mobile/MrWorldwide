package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Gustavo on 5/6/18.
 */
data class Video(var description: String, var url: String, override var geoLocation: LatLng?) : Event(EventType.VIDEO, geoLocation)