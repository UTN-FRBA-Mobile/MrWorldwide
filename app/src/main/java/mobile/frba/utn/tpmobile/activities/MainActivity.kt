package mobile.frba.utn.tpmobile.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.fragments.RunMapFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.menu.getItem(2).isChecked = true
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                //R.id.action_bitacora -> selectedFragment = BitacoraFragment()
                // R.id.action_add -> selectedFragment = AddFragment()
                R.id.action_map -> selectedFragment = RunMapFragment()
                //R.id.action_travelers -> selectedFragment = TravelersFragment()
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, selectedFragment)
            transaction.commit()
            return@setOnNavigationItemSelectedListener true
        }

        //Manually displaying the first fragment - one time only
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, RunMapFragment())
        transaction.commit()
    }
}