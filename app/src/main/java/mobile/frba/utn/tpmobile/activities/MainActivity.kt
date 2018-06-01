package mobile.frba.utn.tpmobile.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.fragments.BitacoraFragment
import mobile.frba.utn.tpmobile.fragments.RunMapFragment
import mobile.frba.utn.tpmobile.fragments.TripsFragment
import mobile.frba.utn.tpmobile.services.FacebookService
import mobile.frba.utn.tpmobile.fragments.TravelersFragment
import mobile.frba.utn.tpmobile.singletons.Navigator
import net.danlew.android.joda.JodaTimeAndroid

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!FacebookService.sessionAlive()){
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        JodaTimeAndroid.init(this);
        setContentView(R.layout.main_activity)
        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        Navigator.setNavigationView(bottomNavigationView)
    }
}
