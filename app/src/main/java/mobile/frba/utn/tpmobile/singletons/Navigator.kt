package mobile.frba.utn.tpmobile.singletons

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.fragments.*
import mobile.frba.utn.tpmobile.models.Event

object Navigator {
    private lateinit var bottomNavigationViewAccess: ()->BottomNavigationView
    public lateinit var supportFragmentManager : FragmentManager

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
            replaceFragment(selectedFragment)
            return@setOnNavigationItemSelectedListener true
        }

        //Manually displaying the first fragment - one time only
        replaceFragment(RunMapFragment())
    }

    private fun replaceFragment(selectedFragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, selectedFragment)
        transaction.commit()
    }

    fun navigateTo(fragment : NavigatorFragment){

        var transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,fragment as Fragment)
        transaction.commit()
        bottomNavigationUpdate(fragment)
    }

    fun bottomNavigationUpdate(fragment : NavigatorFragment){
        fragment.navigatorId?.let { bottomNavigationViewAccess.invoke().menu.findItem(it).isChecked  = true }
    }
    fun navigateTo(event: Event){
        val fragment = BitacoraFragment()
        fragment.showOnlyEvent=event
        replaceFragment(fragment)
        bottomNavigationUpdate(fragment)
    }
}