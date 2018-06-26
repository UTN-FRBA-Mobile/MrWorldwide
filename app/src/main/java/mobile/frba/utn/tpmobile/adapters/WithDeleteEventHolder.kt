package mobile.frba.utn.tpmobile.adapters

import android.view.View
import android.widget.LinearLayout
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.fragments.BitacoraFragment
import mobile.frba.utn.tpmobile.models.Event
import mobile.frba.utn.tpmobile.singletons.Navigator
import mobile.frba.utn.tpmobile.singletons.RepoEvents

/**
 * Created by Gustavo on 6/25/18.
 */
interface WithDeleteEventHolder {
    fun activatedDeleteButton(event : Event, itemView : View){
        val deleteButton : LinearLayout = itemView.findViewById(R.id.bitacora_delete_button )
        deleteButton.setOnClickListener {
            RepoEvents.deleteEvent(event, { Navigator.navigateTo(BitacoraFragment())})
        }
    }
}