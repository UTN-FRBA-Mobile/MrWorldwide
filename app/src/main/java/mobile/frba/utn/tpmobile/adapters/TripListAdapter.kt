package mobile.frba.utn.tpmobile.adapters

import android.support.v4.app.Fragment
import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import mobile.frba.utn.tpmobile.ImageLoader
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.activities.DateFormatter
import mobile.frba.utn.tpmobile.activities.MainActivity
import mobile.frba.utn.tpmobile.fragments.BitacoraFragment
import mobile.frba.utn.tpmobile.fragments.CreateEditTripFragment
import mobile.frba.utn.tpmobile.fragments.TripsFragment
import mobile.frba.utn.tpmobile.models.Trip
import mobile.frba.utn.tpmobile.services.RestClient
import mobile.frba.utn.tpmobile.singletons.Navigator
import mobile.frba.utn.tpmobile.singletons.RepoTrips
import org.jetbrains.anko.alert

/**
 * Created by Gustavo on 5/6/18.
 */
class TripListAdapter(val fr: Fragment, private  var trips: List<Trip>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = trips.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TripListAdapter.TripViewHolder).bind(trips[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return TripListAdapter.TripViewHolder(fr, LayoutInflater.from(parent.context).inflate(R.layout.trip_item, parent, false))
    }

    class TripViewHolder(val fr: Fragment,tripView: View): RecyclerView.ViewHolder(tripView) {
        private val titleView : TextView = tripView.findViewById(R.id.trip_title)
        private val dateView : TextView = tripView.findViewById(R.id.trip_date)
        private val photoView : ImageView = tripView.findViewById(R.id.trip_image)
        private val editButton : View = tripView.findViewById(R.id.edit_trip)
        private val deleteButton : View = tripView.findViewById(R.id.delete_trip)
        private val viewContext = tripView.context

        fun bind(trip: Trip) = with(trip) {
            titleView.text = title
            dateView.text = "Del ${DateFormatter.format(startDate)} al ${DateFormatter.format(finishDate)}"
            ImageLoader.loadImageIn(photoView, tripPhoto.url)
            var selectedFragment = BitacoraFragment()
            RepoTrips.getTrip(fr, trip.id!!).invoke { trip ->

                selectedFragment.trip = trip
                Navigator.supportFragmentManager.fragments.first().activity?.runOnUiThread {
                    photoView.setOnClickListener({
                        Navigator.navigateTo(selectedFragment )
                    })
                }
            }

            editButton.setOnClickListener {
                var bundle = Bundle()
                bundle.putSerializable("trip", trip)
                if (!RestClient.isOnline()){
                    Toast.makeText(Navigator.supportFragmentManager.fragments.first().activity, "No hay conectividad, intente más tarde.", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                val createEditTripFragment = CreateEditTripFragment()
                createEditTripFragment.arguments = bundle
                Navigator.navigateTo(createEditTripFragment)
            }

            deleteButton.setOnClickListener {
                if (!RestClient.isOnline()){
                    Toast.makeText(Navigator.supportFragmentManager.fragments.first().activity, "No hay conectividad, intente más tarde.", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                val builder = AlertDialog.Builder(viewContext)

                builder.setMessage("¿Seguro que deseas borrar el viaje?")

                builder.setPositiveButton("Aceptar"){ dialog, _ ->
                    RepoTrips.deleteTrip(trip, {
                        Navigator.navigateTo(TripsFragment());
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
}