package mobile.frba.utn.tpmobile.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.fragments.MainActivityFragment
import mobile.frba.utn.tpmobile.fragments.MyProfileFragment
import mobile.frba.utn.tpmobile.models.getEventFromJson
import mobile.frba.utn.tpmobile.services.FacebookService
import mobile.frba.utn.tpmobile.singletons.Navigator
import net.danlew.android.joda.JodaTimeAndroid
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


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

    fun impactIntent(){
        var userId =intent?.data?.getQueryParameter("userId")
        var tripId =intent?.data?.getQueryParameter("tripId")
        var eventId =intent?.data?.getQueryParameter("eventId")
        if(!eventId.isNullOrEmpty()) {
            OkHttpClient().newCall(Request.Builder().url(applicationContext.getString(R.string.back_url) + "/event/$userId/$tripId/$eventId").build())
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    throw Error("rompio todo!")
                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonEvent = JSONObject(response.body()!!.string())
                    val event = getEventFromJson(jsonEvent)
                    runOnUiThread{Navigator.navigateTo(event)}
                }
            })
        }
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
