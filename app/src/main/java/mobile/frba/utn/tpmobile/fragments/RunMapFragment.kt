package mobile.frba.utn.tpmobile.fragments

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
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
    }

    override fun onMapReady(map: GoogleMap) {
        RepoTrips.getTripsWithEvents().invoke { trips ->
            updatePins(map, trips)
        }
        map.setOnMarkerClickListener(this)
    }

    fun updatePins(map: GoogleMap, trips : List<Trip>){
        var filteredTrips = trips.filter { it.startDate.isBeforeNow }
        val colorSeparation = 360f / filteredTrips.size
        filteredTrips.forEachIndexed { index, trip ->
            val color = index * colorSeparation
            trip.events.forEach {
                val geoLocation = it.geoLocation
                if (geoLocation != null) {
                    activity?.runOnUiThread({
                        markerMap?.put(map.addMarker(MarkerOptions().position(geoLocation)
                                .icon(BitmapDescriptorFactory.defaultMarker(color)).title(trip.title)), it)
                    })
                }
            }
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        Navigator.navigateTo(markerMap?.get(marker)!!)
        return true
    }
}
