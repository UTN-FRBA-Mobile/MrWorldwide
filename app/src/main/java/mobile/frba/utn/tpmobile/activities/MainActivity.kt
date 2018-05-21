package mobile.frba.utn.tpmobile.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.singletons.Navigator
import net.danlew.android.joda.JodaTimeAndroid

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JodaTimeAndroid.init(this)
        setContentView(R.layout.main_activity)
        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        Navigator.navigator.setNavigationView(bottomNavigationView)
    }
}
