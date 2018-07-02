package mobile.frba.utn.tpmobile.adapters

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.LinearLayout
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.models.Event

interface AdapterWithSharedButtonHolder{

    fun activatedSharedButton(event : Event, itemView : View){
        val sharedButton : LinearLayout = itemView.findViewById(R.id.shared_button)
        sharedButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(itemView.context)
            val url = "http://mr.world.wide?userId=${event.urlUserId()}&ownerId=${event.tripId}&eventId=${event.id}"
            alertDialog.setTitle("Link para compartir")
            alertDialog.setMessage(url)
            alertDialog.setPositiveButton("copiar",{dialog, _ ->
                ((itemView.context as Activity)
                        .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
                        .primaryClip = ClipData.newPlainText("url",url)
                dialog.cancel()
            })
            alertDialog.show()
        }
    }
}