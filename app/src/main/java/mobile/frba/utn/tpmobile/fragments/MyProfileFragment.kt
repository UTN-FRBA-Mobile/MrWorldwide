package mobile.frba.utn.tpmobile.fragments

import android.widget.Button
import mobile.frba.utn.tpmobile.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.frba.utn.tpmobile.adapters.BitacoraListAdapter
import mobile.frba.utn.tpmobile.models.Photo
import mobile.frba.utn.tpmobile.models.Text


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