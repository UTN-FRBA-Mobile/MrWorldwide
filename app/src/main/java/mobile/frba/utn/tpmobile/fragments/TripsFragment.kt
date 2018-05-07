package mobile.frba.utn.tpmobile.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.adapters.TripListAdapter
import mobile.frba.utn.tpmobile.models.Photo
import mobile.frba.utn.tpmobile.models.Trip
import org.joda.time.DateTime
import java.sql.Date

class TripsFragment : Fragment() {
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trips, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val romaPhoto = Photo("https://www.losmundosdeceli.com/fin-de-semana-en-roma/", DateTime.now())
        val romaTrip = Trip("Roma", romaPhoto, Date(2018, 2, 10), Date(2018, 2, 22), emptyList())
        val nyPhoto = Photo("https://www.lonelyplanet.com/usa/new-york-city", DateTime.now())
        val nyTrip = Trip("New York", nyPhoto, Date(2019, 2, 10), Date(2019, 2, 22), emptyList())

        recyclerView = getView()!!.findViewById(R.id.trip_list)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = TripListAdapter(listOf(romaTrip, nyTrip))
    }
}

