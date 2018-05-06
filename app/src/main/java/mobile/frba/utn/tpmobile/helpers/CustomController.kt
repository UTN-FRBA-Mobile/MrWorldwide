package mobile.frba.utn.tpmobile.helpers

import android.content.Context
import android.view.View
import android.widget.MediaController

class CustomController(context: Context, anchor: View) : MediaController(context) {
    init {
        super.setAnchorView(anchor)
    }

    override fun setAnchorView(view: View) {
        // Do nothing
    }
}