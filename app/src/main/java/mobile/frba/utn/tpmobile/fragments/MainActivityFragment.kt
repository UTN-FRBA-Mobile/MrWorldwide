package mobile.frba.utn.tpmobile.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.fragments.BitacoraFragment
import mobile.frba.utn.tpmobile.fragments.RunMapFragment
import mobile.frba.utn.tpmobile.fragments.TripsFragment
import mobile.frba.utn.tpmobile.services.FacebookService
import mobile.frba.utn.tpmobile.fragments.TravelersFragment
import mobile.frba.utn.tpmobile.singletons.Navigator
import net.danlew.android.joda.JodaTimeAndroid

class MainActivityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_activity_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bottomNavigationView: BottomNavigationView = view!!.findViewById(R.id.bottom_navigation)
        Navigator.setNavigationView(bottomNavigationView)
    }
}
