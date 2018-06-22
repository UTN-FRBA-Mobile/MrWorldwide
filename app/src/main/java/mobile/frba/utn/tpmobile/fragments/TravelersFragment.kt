package mobile.frba.utn.tpmobile.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.adapters.TravelerActivityListAdapter
import mobile.frba.utn.tpmobile.singletons.RepoTrips


class TravelersFragment : NavigatorFragment(R.id.action_travelers) {
	lateinit var recyclerView: RecyclerView


	override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.travelers_view, parent, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		recyclerView = getView()!!.findViewById(R.id.travelers_view_list)
		recyclerView.layoutManager=LinearLayoutManager(activity)
		RepoTrips.getFriendEvents().invoke{events -> activity?.runOnUiThread{  recyclerView.adapter = TravelerActivityListAdapter(events)}}

	}
}