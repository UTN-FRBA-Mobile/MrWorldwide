package mobile.frba.utn.tpmobile.fragments

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import mobile.frba.utn.tpmobile.models.Event
import mobile.frba.utn.tpmobile.models.Trip
import mobile.frba.utn.tpmobile.singletons.Navigator
import mobile.frba.utn.tpmobile.singletons.RepoTrips

class RunMapFragment: SupportMapFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    var selectedTrip : Trip? = null

    var alreadyStartedTrips : List<Trip>? = null
    var markerMap : MutableMap<Marker, Event>? = HashMap()
    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        getMapAsync(this)
        alreadyStartedTrips = RepoTrips.trips.filter { it.startDate.isBeforeNow }
        selectedTrip = RepoTrips.getActualTripFor(2)
        if(selectedTrip==null){
            selectedTrip = alreadyStartedTrips?.maxBy { it.finishDate}
        }

    }

    override fun onMapReady(map: GoogleMap) {
        selectedTrip?.events?.forEach {
            val geoLocation = it.geoLocation
            if(geoLocation!=null) {
               markerMap?.put(map.addMarker(MarkerOptions().position(geoLocation)),it)
            }
        }
        map.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        Navigator.navigateTo(markerMap?.get(marker)!!)
        return true
    }
}
