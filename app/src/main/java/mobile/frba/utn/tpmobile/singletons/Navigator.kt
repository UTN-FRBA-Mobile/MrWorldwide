package mobile.frba.utn.tpmobile.Singletons

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.fragments.*

 object Navigator {
    private lateinit var bottomNavigationViewAccess: ()->BottomNavigationView
    private lateinit var supportFragmentManager : FragmentManager

    fun setNavigationView (bottomNavigationView: BottomNavigationView){
        bottomNavigationViewAccess = {bottomNavigationView}
        bottomNavigationViewAccess.invoke().menu.getItem(2).isChecked = true
         supportFragmentManager = (bottomNavigationViewAccess.invoke().context as FragmentActivity).supportFragmentManager

        bottomNavigationViewAccess.invoke().setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.action_bitacora -> selectedFragment = BitacoraFragment()
                R.id.action_map -> selectedFragment = RunMapFragment()
                R.id.action_trips -> selectedFragment = TripsFragment()
                R.id.action_travelers -> selectedFragment = TravelersFragment()
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

    fun navigateTo(fragment : NavigatorFragment){
        var transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,fragment as Fragment)
        transaction.commit()
        fragment.navigatorId?.let { bottomNavigationViewAccess.invoke().menu.findItem(it).isChecked  = true }

    }
}