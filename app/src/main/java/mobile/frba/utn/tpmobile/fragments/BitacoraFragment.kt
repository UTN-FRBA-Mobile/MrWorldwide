package mobile.frba.utn.tpmobile.fragments

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.singletons.Navigator
import mobile.frba.utn.tpmobile.singletons.RepoTrips
import mobile.frba.utn.tpmobile.adapters.BitacoraListAdapter
import mobile.frba.utn.tpmobile.models.Event
import mobile.frba.utn.tpmobile.models.Trip


class BitacoraFragment : NavigatorFragment(R.id.action_bitacora) {
    lateinit var recyclerView: RecyclerView
     var trip : Trip? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bitacora_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var events : List<Event> = emptyList()
        recyclerView = getView()!!.findViewById(R.id.bitacora_list)
        recyclerView.layoutManager=LinearLayoutManager(activity)
        if (trip != null) {
            events = trip!!.events
        }
        else{
                val actualTrip : Trip? = RepoTrips.repo.getActualTripFor(2)
                if(actualTrip != null){
                    events = actualTrip.events
                }
                else{
                    showNextTripMessages(2)
                }
        }
        recyclerView.adapter = BitacoraListAdapter(events)

    }
    fun showNextTripMessages(userId : Int){
        val nextTrip : Trip? = RepoTrips.repo.getNextTripFor(2)
        var message: String
        val builder1 = AlertDialog.Builder(context!!)
        builder1.setCancelable(true)
        builder1.setPositiveButton(
                "Yes",
                { dialog, _ ->
                    val createEditTripFragment = CreateEditTripFragment()
                    Navigator.navigator.navigateTo(createEditTripFragment)
                    dialog.cancel() })

        builder1.setNegativeButton(
                "No",
                { dialog, _ -> dialog.cancel() })

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
        val alert11 = builder1.create()
        alert11.show()

    }
}