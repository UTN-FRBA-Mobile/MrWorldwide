package mobile.frba.utn.tpmobile.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.adapters.TravelerActivityListAdapter
import mobile.frba.utn.tpmobile.models.Photo
import mobile.frba.utn.tpmobile.models.Text
import mobile.frba.utn.tpmobile.models.Video
import org.joda.time.DateTime


class TravelersFragment : NavigatorFragment(R.id.action_travelers) {
	lateinit var recyclerView: RecyclerView


	override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
		var view = inflater.inflate(R.layout.travelers_view, parent, false)
		return view
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val photo = Photo("asd", DateTime.now(), "LALALALA")
		val text = Text("Soy un titulo de relleno", DateTime.now(),"Soy un texto de relleno")
		val video = Video("Soy un titulo de relleno", "saraza")

		recyclerView = getView()!!.findViewById(R.id.travelers_view_list)
		recyclerView.layoutManager= LinearLayoutManager(activity)
		recyclerView.adapter = TravelerActivityListAdapter(listOf(photo,text,video))
	}
}