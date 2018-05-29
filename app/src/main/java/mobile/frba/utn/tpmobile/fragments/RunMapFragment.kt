package mobile.frba.utn.tpmobile.fragments

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import mobile.frba.utn.tpmobile.models.Event
import mobile.frba.utn.tpmobile.models.Trip
import mobile.frba.utn.tpmobile.singletons.Navigator
import mobile.frba.utn.tpmobile.singletons.RepoTrips

class RunMapFragment : SupportMapFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    var alreadyStartedTrips: List<Trip>? = null
    var markerMap: MutableMap<Marker, Event>? = HashMap()
    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        getMapAsync(this)
        alreadyStartedTrips = RepoTrips.trips.filter { it.startDate.isBeforeNow }
    }

    override fun onMapReady(map: GoogleMap) {
        val trips = alreadyStartedTrips
        if(trips!=null) {
            val colorSeparation  = 360f / trips.size
            trips.forEachIndexed {index, trip ->
                val color = index*colorSeparation
                trip.events.forEach {
                    val geoLocation = it.geoLocation
                    if (geoLocation != null) {
                        markerMap?.put(map.addMarker(MarkerOptions().position(geoLocation)
                                .icon(BitmapDescriptorFactory.defaultMarker(color)).title(trip.title)), it)
                    }
                }
            }
            map.setOnMarkerClickListener(this)
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        Navigator.navigateTo(markerMap?.get(marker)!!)
        return true
    }
}
