package mobile.frba.utn.tpmobile.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.services.FacebookService
import net.danlew.android.joda.JodaTimeAndroid
import android.support.v4.view.ViewPager
import android.support.v4.app.FragmentPagerAdapter
import mobile.frba.utn.tpmobile.fragments.*


class MainActivity : AppCompatActivity() {
    var adapterViewPager: FragmentPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JodaTimeAndroid.init(this)
        if (!FacebookService.sessionAlive()){
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }
        setContentView(R.layout.main_activity)
        val vpPager = findViewById(R.id.viewPager) as ViewPager
        adapterViewPager = MyPagerAdapter(supportFragmentManager)
        vpPager.adapter = adapterViewPager
    }
    class MyPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

        // Returns total number of pages
        override fun getCount(): Int {
            return NUM_ITEMS
        }

        // Returns the fragment to display for that page
        override fun getItem(position: Int): Fragment? {
            return when (position) {
                0 // Fragment # 0 - This will show FirstFragment
                -> MainActivityFragment()
                1 // Fragment # 0 - This will show FirstFragment different title
                -> MyProfileFragment()
                else -> null
            }
        }

        // Returns the page title for the top indicator
        override fun getPageTitle(position: Int): CharSequence? {
            return "Page $position"
        }

        companion object {
            private val NUM_ITEMS = 2
        }




    }

}
