package mobile.frba.utn.tpmobile.services

import com.facebook.AccessToken


open class FacebookService {

    companion object {
        fun sessionAlive(): Boolean {
            val accessToken = AccessToken.getCurrentAccessToken()
            val loggedIn = accessToken != null
            val isExpired = accessToken?.isExpired
            return loggedIn  && !(isExpired?:true)
        }
    }
}