package mobile.frba.utn.tpmobile.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.activities.MainActivity
import mobile.frba.utn.tpmobile.adapters.BitacoraListAdapter
import mobile.frba.utn.tpmobile.models.Event
import mobile.frba.utn.tpmobile.models.Trip
import mobile.frba.utn.tpmobile.singletons.Navigator
import mobile.frba.utn.tpmobile.singletons.RepoTrips


class BitacoraFragment : NavigatorFragment(R.id.action_bitacora) {

    lateinit var recyclerView: RecyclerView
    var trip : Trip? = null
    lateinit var mainActivity : MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bitacora_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity

        var events: List<Event>
        recyclerView = getView()!!.findViewById(R.id.bitacora_list)
        recyclerView.layoutManager=LinearLayoutManager(activity)
        if (trip != null) {
            events = trip!!.events
            activity?.runOnUiThread  {recyclerView.adapter = BitacoraListAdapter(events)}
        }
        else{
                RepoTrips.getActualTripFor().invoke { actualTrip ->
                    if(actualTrip != null){
                        events = actualTrip.events
                        activity?.runOnUiThread{ recyclerView.adapter = BitacoraListAdapter(events)}
                    }
                    else{
                        showNextTripMessages()
                    }
                }
            }
        }

    fun showNextTripMessages(){
        var message: String
        val builder1 = AlertDialog.Builder(context!!)
        builder1.setCancelable(true)
        builder1.setPositiveButton(
                "Yes",
                { dialog, _ ->
                    val createEditTripFragment = CreateEditTripFragment()
                    Navigator.navigateTo(createEditTripFragment)
                    dialog.cancel() })

        builder1.setNegativeButton(
                "No",
                { dialog, _ -> dialog.cancel() })

        RepoTrips.getNextTripFor().invoke { nextTrip ->
            message = if(nextTrip != null){
                "Actualmente no te encuentras en ningún viaje. " +
                        "Tu próximo viaje es el " +
                        nextTrip.startDate.toString("dd/MM/yyyy") +
                        ". ¿Querés registrar otro?"
            }
            else{
                "Actualmente no te encuentras en ningún viaje ni tenemos viajes próximos registrados. ¿Querés registrar uno?"
            }
            builder1.setMessage(message)
            val mHandler = object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(message: Message) {
                    val alert11 = builder1.create()
                    alert11.show()
                }
            }
            val message = mHandler.obtainMessage()
            message.sendToTarget()
        }
    }
}