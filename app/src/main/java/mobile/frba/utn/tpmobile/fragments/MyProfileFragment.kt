package mobile.frba.utn.tpmobile.fragments

import android.content.Context
import android.content.Intent
import mobile.frba.utn.tpmobile.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.GraphRequest
import org.jetbrains.anko.doAsync
import android.graphics.drawable.Drawable
import mobile.frba.utn.tpmobile.services.RestClient
import java.net.URL
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import java.io.ByteArrayOutputStream


class MyProfileFragment: Fragment() {
    val PREFS_FILENAME = "mobile.frba.utn.tpmobile.prefs"
    var prefs: SharedPreferences? = null
    var userName: String? = null
    var urlUserImg: String? = null
    val callbackManager = CallbackManager.Factory.create()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val namePlaceholder = view!!.findViewById(R.id.userNameValue) as EditText
        prefs = this.context!!.getSharedPreferences(
                PREFS_FILENAME, Context.MODE_PRIVATE)
        if (!RestClient.isOnline()){
            getDataFromPrefs(view)
        } else {
            val accessToken = AccessToken.getCurrentAccessToken()
            val request = GraphRequest.newMeRequest(
                    accessToken
            ) { jsonObject, response ->
                try {
                    userName = jsonObject.getString("name")
                    urlUserImg = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url")
                    val imgUrl = URL(urlUserImg)
                    namePlaceholder.setText(userName)
                    setUserImageAsync(imgUrl, view)
                }  catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            val parameters = Bundle()
            parameters.putString("fields", "name, picture.width(150).height(150)")
            request.parameters = parameters
            request.executeAsync()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }



    fun getDataFromPrefs(view: View){
        userName = prefs!!.getString("userName", "userName")
        val userImgBase64 = prefs!!.getString("usrImg", "")
        val encodeByte = Base64.decode(userImgBase64, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        val drawableUserImg = BitmapDrawable(bitmap)
        val namePlaceholder = view!!.findViewById(R.id.userNameValue) as EditText
        namePlaceholder.setText(userName)
        val imgContainer = view!!.findViewById(R.id.userProfileImage) as ImageView
        imgContainer.setImageDrawable(drawableUserImg)
    }

    fun saveProfileToPrefs(imgDrawable: Drawable){
        val editor = prefs!!.edit()
        val realImage = (imgDrawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        realImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        val imgBase64String = Base64.encodeToString(b, Base64.DEFAULT)
        editor.putString("userName", userName)
        editor.putString("usrImg", imgBase64String)
        editor.apply()
    }

    fun setUserImageAsync(imgUrl: URL, view: View) {
        doAsync {
            val imgDrawable = Drawable.createFromStream(imgUrl.openStream(), "src")
            saveProfileToPrefs(imgDrawable)
            val imgContainer = view!!.findViewById(R.id.userProfileImage) as ImageView
            getActivity()!!.runOnUiThread({
                imgContainer.setImageDrawable(imgDrawable)
            })
        }
    }
}