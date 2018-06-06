package mobile.frba.utn.tpmobile.daos

import android.os.AsyncTask
import android.arch.lifecycle.LiveData
import android.app.Application
import mobile.frba.utn.tpmobile.models.Trip


class TripsRepository internal constructor(application: Application) {

    private val mTripDao: TripDao
    internal val mAllTrips: LiveData<List<Trip>>

    init {
        val db = WorldRoomDatabase.getDatabase(application)
        mTripDao = db!!.tripDao()
        mAllTrips = mTripDao.allTrips
    }

    fun insert(trip: Trip) {
        insertAsyncTask(mTripDao).execute(trip)
    }

    fun getAllTrips(): LiveData<List<Trip>> {
        return mAllTrips
    }

    fun getTrip(id: Int): LiveData<Trip> {
        return mTripDao.getTrip(id.toString())
    }


    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: TripDao) : AsyncTask<Trip, Void, Void>() {

        override fun doInBackground(vararg params: Trip): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}