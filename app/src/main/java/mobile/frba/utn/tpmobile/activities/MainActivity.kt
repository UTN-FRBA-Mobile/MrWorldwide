package mobile.frba.utn.tpmobile.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.GraphRequest
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.connectivity.NetworkChangeReceiver
import mobile.frba.utn.tpmobile.fragments.MainActivityFragment
import mobile.frba.utn.tpmobile.fragments.MyProfileFragment
import mobile.frba.utn.tpmobile.models.getEventFromJson
import mobile.frba.utn.tpmobile.services.FacebookService
import mobile.frba.utn.tpmobile.singletons.Navigator
import mobile.frba.utn.tpmobile.singletons.RepoEvents
import mobile.frba.utn.tpmobile.singletons.RepoTrips
import net.danlew.android.joda.JodaTimeAndroid
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import android.content.IntentFilter
import mobile.frba.utn.tpmobile.singletons.AppHolder


class MainActivity : AppCompatActivity() {
    private var adapterViewPager: FragmentPagerAdapter? = null
    private val networkChangeReceiver = NetworkChangeReceiver(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JodaTimeAndroid.init(this)
        val netFilter = IntentFilter()
        netFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        AppHolder.getContext().registerReceiver(networkChangeReceiver,netFilter)
        if (!FacebookService.sessionAlive()){
            startActivity(Intent(this, LoginActivity::class.java))
            loadUserName()
            return
        }
        loadUserName()
        setContentView(R.layout.main_activity)
        val vpPager = findViewById<ViewPager>(R.id.viewPager)
        adapterViewPager = MyPagerAdapter(supportFragmentManager)
        vpPager.adapter = adapterViewPager
    }

    private fun loadUserName(){
        GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                { jsonObject, response ->
                    try {
                        val userName = jsonObject.getString("name")
                        RepoTrips.userId = userName
                        RepoEvents.userId = userName
                    }  catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
        ).executeAsync()
    }
    fun impactIntent(){
        var userId =intent?.data?.getQueryParameter("userId")
        var tripId =intent?.data?.getQueryParameter("ownerId")
        var eventId =intent?.data?.getQueryParameter("eventId")
        if(!eventId.isNullOrEmpty()) {
            OkHttpClient().newCall(Request.Builder().url(RepoTrips.backUrl + "/event/$userId/$tripId/$eventId").build())
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
