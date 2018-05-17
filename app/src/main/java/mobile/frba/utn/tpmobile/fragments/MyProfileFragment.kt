package mobile.frba.utn.tpmobile.fragments

import android.content.Intent
import android.widget.Button
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
import java.net.URL


class MyProfileFragment: Fragment() {
    val callbackManager = CallbackManager.Factory.create()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSaveChanges = getView()!!.findViewById(R.id.saveChanges) as Button
        btnSaveChanges.setOnClickListener {
            //save changes
        }
        val namePlaceholder = view!!.findViewById(R.id.userNameValue) as EditText
        val accessToken = AccessToken.getCurrentAccessToken()
        val request = GraphRequest.newMeRequest(
                accessToken
        ) { jsonObject, response ->
            try {
                val userName = jsonObject.getString("name")
                val urlUserImg = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun setUserImageAsync(imgUrl: URL, view: View) {
        doAsync {
            val imgDrawable = Drawable.createFromStream(imgUrl.openStream(), "src")
            val imgContainer = view!!.findViewById(R.id.userProfileImage) as ImageView
            imgContainer.setImageDrawable(imgDrawable)
        }
    }
}