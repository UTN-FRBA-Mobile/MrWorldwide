package mobile.frba.utn.tpmobile.Singletons

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.fragments.*

open class Navigator{
    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var supportFragmentManager : FragmentManager
    private constructor(){

    }

    companion object Factory {
         var navigator = Navigator()
    }

    fun setNavigationView(view : BottomNavigationView){
        bottomNavigationView = view
        bottomNavigationView.menu.getItem(2).isChecked = true
         supportFragmentManager = (bottomNavigationView.context as FragmentActivity).supportFragmentManager

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.action_bitacora -> selectedFragment = BitacoraFragment() as Fragment
                R.id.action_map -> selectedFragment = RunMapFragment()
                R.id.action_trips -> selectedFragment = TripsFragment() as Fragment
                R.id.action_travelers -> selectedFragment = TravelersFragment() as Fragment
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
        fragment.navigatorId?.let { bottomNavigationView.menu.findItem(it).isChecked  = true }

    }
}