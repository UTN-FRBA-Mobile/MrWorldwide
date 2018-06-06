package mobile.frba.utn.tpmobile.services

import android.content.Context
import android.net.ConnectivityManager
import mobile.frba.utn.tpmobile.singletons.AppHolder
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.InputStream


open class RestClient {



    companion object {

        fun get(url: String): InputStream {
            val request = Request.Builder().url(url).build()
            val response = OkHttpClient().newCall(request).execute()
            val body = response.body()
            // body.toString() returns a string representing the object and not the body itself, probably
            // kotlins fault when using third party libraries. Use byteStream() and convert it to a String
            return body!!.byteStream()
        }


        fun isOnline(): Boolean {
            val cm = AppHolder.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }

    }


}