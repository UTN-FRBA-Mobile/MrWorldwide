package mobile.frba.utn.tpmobile.connectivity

import android.net.ConnectivityManager
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import android.widget.Toast
import android.support.v4.content.ContextCompat.startActivity



class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val connMgr = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)

        val mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        if (wifi.isAvailable || mobile.isAvailable) {
            //Context ctx = AppHolder.getApp().getBaseContext()
            val i = context.getPackageManager()
                    .getLaunchIntentForPackage(context.getPackageName())
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(context, i, null)
        }
    }
}
