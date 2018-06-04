package mobile.frba.utn.tpmobile.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.singletons.Navigator
import mobile.frba.utn.tpmobile.singletons.RepoTrips
import mobile.frba.utn.tpmobile.adapters.TripListAdapter


class TripsFragment : NavigatorFragment(null) {
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trips, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = getView()!!.findViewById(R.id.trip_list)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val activity = this.activity
        RepoTrips.getTrips().invoke { trips ->
            activity?.runOnUiThread { recyclerView.adapter = TripListAdapter(trips) }
        }
        val addButton: FloatingActionButton = getView()!!.findViewById<View>(R.id.trip_add) as FloatingActionButton
        addButton.setOnClickListener {
            addButton.hide()
            val createEditTripFragment = CreateEditTripFragment()
            Navigator.navigateTo(createEditTripFragment)
        }
    }
}

