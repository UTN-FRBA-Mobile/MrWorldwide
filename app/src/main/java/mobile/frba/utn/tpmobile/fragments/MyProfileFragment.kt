package mobile.frba.utn.tpmobile.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import mobile.frba.utn.tpmobile.R


class MyProfileFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSaveChanges = getView()!!.findViewById(R.id.saveChanges) as Button
        btnSaveChanges.setOnClickListener {
            //save changes
        }
    }
}