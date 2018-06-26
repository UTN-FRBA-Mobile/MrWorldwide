package mobile.frba.utn.tpmobile.adapters

import android.app.AlertDialog
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
            val builder = AlertDialog.Builder(itemView.context)

            builder.setMessage("¿Seguro que deseas borrar este evento?")

            builder.setPositiveButton("Aceptar"){ dialog, _ ->
                RepoEvents.deleteEvent(event, {
                    Navigator.navigateTo(BitacoraFragment())
                    dialog.cancel()
                })
            }

            builder.setNegativeButton("Cancelar"){ dialog, _ ->
                dialog.cancel()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}