package mobile.frba.utn.tpmobile.connectivity

import android.net.ConnectivityManager
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import android.widget.Toast


class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val connMgr = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)

        val mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        if (wifi.isAvailable || mobile.isAvailable) {
            Toast.makeText(
                    context,
                    "Connectivity back!",
                    Toast.LENGTH_LONG).show()
        }
    }
}
