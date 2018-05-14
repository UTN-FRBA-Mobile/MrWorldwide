package mobile.frba.utn.tpmobile.fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import mobile.frba.utn.tpmobile.R



class TravelersFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
		var view = inflater.inflate(R.layout.travelers_view, parent, false)
		val childFragment = newVideoFragment("android.resource://" + this.activity!!.packageName + "/" + R.raw.video_example)
		val transaction = childFragmentManager.beginTransaction()
		transaction.replace(R.id.child_fragment_container, childFragment).commit()
		return view
	}
}