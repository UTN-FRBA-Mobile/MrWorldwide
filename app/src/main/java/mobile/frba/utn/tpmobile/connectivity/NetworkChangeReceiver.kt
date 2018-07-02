package mobile.frba.utn.tpmobile.connectivity

import android.net.ConnectivityManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.widget.Toast
import android.support.v4.content.ContextCompat.startActivity
import mobile.frba.utn.tpmobile.singletons.RepoTrips
import android.net.NetworkInfo
import mobile.frba.utn.tpmobile.activities.MainActivity

class NetworkChangeReceiver(val maContext: MainActivity) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            //Context ctx = AppHolder.getApp().getBaseContext()
            RepoTrips.synchronizeLocalTrips(maContext)
            /*val i = context.getPackageManager()
                    .getLaunchIntentForPackage(context.getPackageName())
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(context, i, null)*/
        }
    }
}
