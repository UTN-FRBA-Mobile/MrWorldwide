package mobile.frba.utn.tpmobile.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import mobile.frba.utn.tpmobile.R
import java.text.SimpleDateFormat
import java.util.*

class CreateEditTripFragment : Fragment() {

    var startDate: TextView? = null
    var finishDate: TextView? = null
    var calendar = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_edit_trip, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startDate = view!!.findViewById(R.id.start_date)
        finishDate = view!!.findViewById(R.id.finish_date)

        startDate!!.text = "dd/mm/aaaa"
        finishDate!!.text = "dd/mm/aaaa"


        setDateOnClick(startDate!!)
        setDateOnClick(finishDate!!)
    }

    private fun setDateOnClick(date: TextView) {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView(date)
        }

        date.setOnClickListener {
            DatePickerDialog(activity,
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun updateDateInView(date: TextView) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date.text = sdf.format(calendar.time)
    }



}
