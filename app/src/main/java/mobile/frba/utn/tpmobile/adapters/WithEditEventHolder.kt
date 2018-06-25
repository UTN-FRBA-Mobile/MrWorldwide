package mobile.frba.utn.tpmobile.adapters

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.fragments.CreateEditEventFragment
import mobile.frba.utn.tpmobile.models.Event
import mobile.frba.utn.tpmobile.singletons.Navigator

/**
 * Created by Gustavo on 6/25/18.
 */
interface WithEditEventHolder {
    fun activatedEditButton(event : Event, itemView : View){
        val editButton : LinearLayout = itemView.findViewById(R.id.bitacora_edit_button )
        editButton.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable("event", event)
            val createEditEventFragment = CreateEditEventFragment()
            createEditEventFragment.arguments = bundle
            Navigator.navigateTo(createEditEventFragment)
        }
    }
}