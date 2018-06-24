package mobile.frba.utn.tpmobile.fragments


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.singletons.Navigator
import java.text.SimpleDateFormat
import java.util.*

class CreateEditEventFragment : NavigatorFragment(null) {
    private var date: TextView? = null
    private var calendar = Calendar.getInstance()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_edit_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        date = view.findViewById(R.id.event_date)

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            date!!.text = sdf.format(calendar.time)
        }

        date!!.setOnClickListener {
            DatePickerDialog(activity,
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        onAcceptButtonClick()
        onCancelButtonClick()
    }

    private fun onAcceptButtonClick() {
        val acceptButton = view!!.findViewById<View>(R.id.accept_event)
        //Acá hay que hacer el POST para crear el evento
        acceptButton.setOnClickListener { Navigator.navigateTo(BitacoraFragment()) }
    }

    private fun onCancelButtonClick() {
        val cancelButton = view!!.findViewById<View>(R.id.cancel_event)
        cancelButton.setOnClickListener { Navigator.navigateTo(BitacoraFragment()) }
    }
}
