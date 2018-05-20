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
import mobile.frba.utn.tpmobile.models.TripPhoto
import org.joda.time.DateTime
import java.sql.Date


class TripsFragment : Fragment() {
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trips, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val romaPhoto = TripPhoto("https://www.losmundosdeceli.com/wp-content/uploads/2017/05/coliseo_roma_atardecer-1080x640.jpg", DateTime.now())
        val romaTrip = Trip("Roma", romaPhoto, DateTime().withDate(2018, 2, 10), DateTime().withDate(2018, 2, 22), emptyList())
        val nyPhoto = TripPhoto("https://brightcove04pmdo-a.akamaihd.net/5104226627001/5104226627001_5244714388001_5205235439001-vs.jpg?pubId=5104226627001&videoId=5205235439001", DateTime.now())
        val nyTrip = Trip("New York", nyPhoto, DateTime().withDate(2019, 2, 10), DateTime().withDate(2019, 2, 22), emptyList())

        recyclerView = getView()!!.findViewById(R.id.trip_list)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = TripListAdapter(listOf(romaTrip, nyTrip))

        val addButton = getView()!!.findViewById<View>(R.id.trip_add)
        addButton.setOnClickListener {
            openCreateFragment(view)
        }
    }

    fun openCreateFragment(view: View) {
        val createEditTripFragment = CreateEditTripFragment()
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, createEditTripFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}

