package mobile.frba.utn.tpmobile.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import mobile.frba.utn.tpmobile.models.Trip


@Dao
interface TripDao {

    @get:Query("SELECT * from trip_table ORDER BY id ASC")
    val allTrips: LiveData<List<Trip>>

    @get:Query("SELECT * from trip_table ORDER BY id ASC")
    val loadTripsSync: List<Trip>

    @Query("SELECT * FROM trip_table WHERE id = :id ")
    fun getTrip(id: String): LiveData<Trip>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trip: Trip)

    @Query("DELETE FROM trip_table")
    fun deleteAll()
}