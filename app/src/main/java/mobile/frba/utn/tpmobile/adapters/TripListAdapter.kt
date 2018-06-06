package mobile.frba.utn.tpmobile.adapters

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import mobile.frba.utn.tpmobile.ImageLoader
import mobile.frba.utn.tpmobile.R
import mobile.frba.utn.tpmobile.singletons.Navigator
import mobile.frba.utn.tpmobile.activities.DateFormatter
import mobile.frba.utn.tpmobile.fragments.BitacoraFragment
import mobile.frba.utn.tpmobile.models.Trip
import mobile.frba.utn.tpmobile.singletons.RepoTrips

/**
 * Created by Gustavo on 5/6/18.
 */
class TripListAdapter(val fr: Fragment, var trips: List<Trip>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = trips.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TripListAdapter.TripViewHolder).bind(trips[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return TripListAdapter.TripViewHolder(fr, LayoutInflater.from(parent.context).inflate(R.layout.trip_item, parent, false))
    }

    class TripViewHolder(val fr: Fragment,tripView: View): RecyclerView.ViewHolder(tripView) {
        val titleView : TextView = tripView.findViewById(R.id.trip_title)
        val dateView : TextView = tripView.findViewById(R.id.trip_date)
        val photoView : ImageView = tripView.findViewById(R.id.trip_image)
        fun bind(trip: Trip) = with(trip) {
            titleView.text = title
            dateView.text = "Del ${DateFormatter.format(startDate)} al ${DateFormatter.format(finishDate)}"
            ImageLoader.loadImageIn(photoView, tripPhoto.url)
            var selectedFragment = BitacoraFragment()
            RepoTrips.getTrip(fr, trip.id).invoke { trip ->
                selectedFragment.trip = trip
                Navigator.supportFragmentManager.fragments.first().activity?.runOnUiThread {
                    photoView.setOnClickListener({
                        Navigator.navigateTo(selectedFragment )
                    })
                }
            }
        }
    }
}