package mobile.frba.utn.tpmobile.fragments

import android.widget.Button
import mobile.frba.utn.tpmobile.R
import android.os.Bundle
import android.support.v4.app.FragmentActivity


class MyProfileFragment: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        val btnSaveChanges = findViewById(R.id.saveChanges) as Button
        btnSaveChanges.setOnClickListener {
            //save changes
        }
    }
}