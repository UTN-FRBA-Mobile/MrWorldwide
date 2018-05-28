package mobile.frba.utn.tpmobile.models

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Gustavo on 5/6/18.
 */
open interface Event {
    val viewType: Int
    var geoLocation: LatLng?
}