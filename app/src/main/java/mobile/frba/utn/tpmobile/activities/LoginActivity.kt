package mobile.frba.utn.tpmobile.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import mobile.frba.utn.tpmobile.R

class LoginActivity : AppCompatActivity() {
    val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(application)
        val loginButton = findViewById(R.id.login_button) as LoginButton ;
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Toast.makeText(this@LoginActivity, "Login exitoso!", Toast.LENGTH_LONG).show()
                }

                override fun onCancel() {
                    Toast.makeText(this@LoginActivity, "Login cancelado!", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: FacebookException) {
                    Toast.makeText(this@LoginActivity, "ERROR EN EL LOGIN!", Toast.LENGTH_LONG).show()
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}