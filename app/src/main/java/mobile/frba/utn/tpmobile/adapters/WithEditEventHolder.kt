package mobile.frba.utn.tpmobile.adapters

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.fragments.CreateEditEventFragment
import mobile.frba.utn.tpmobile.models.Event
import mobile.frba.utn.tpmobile.services.RestClient
import mobile.frba.utn.tpmobile.singletons.Navigator

interface WithEditEventHolder {
    fun activatedEditButton(event : Event, itemView : View){
        val editButton : LinearLayout = itemView.findViewById(R.id.bitacora_edit_button )
        editButton.setOnClickListener {
            if (!RestClient.isOnline()){
                Toast.makeText(Navigator.supportFragmentManager.fragments.first().activity, "No hay conectividad, intente más tarde.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            var bundle = Bundle()
            bundle.putSerializable("event", event)
            val createEditEventFragment = CreateEditEventFragment()
            createEditEventFragment.arguments = bundle
            Navigator.navigateTo(createEditEventFragment)
        }
    }
}