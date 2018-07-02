package mobile.frba.utn.tpmobile.daos

import android.os.AsyncTask
import android.arch.lifecycle.LiveData
import android.app.Application
import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import mobile.frba.utn.tpmobile.models.*
import java.util.*


class TripsRepository internal constructor(application: Application) {

    private val mTripDao: TripDao
    private val mTextDao: TextDao
    private val mPhotoDao: PhotoDao
    private val mVideoDao: VideoDao
    private val mImageCreateDao: ImagesDao
    internal val mAllTrips: LiveData<List<Trip>>

    init {
        val db = WorldRoomDatabase.getDatabase(application)
        mTripDao = db!!.tripDao()
        mTextDao = db!!.textDao()
        mPhotoDao = db!!.photoDao()
        mVideoDao = db!!.videoDao()
        mImageCreateDao = db!!.imageCreateDao()
        mAllTrips = mTripDao.allTrips
    }

    fun getAllLocalImages(): List<ImageCreate> {
        return mImageCreateDao.getAll()
    }

    fun deleteImageCreate(img : ImageCreate){
        mImageCreateDao.delete(img)
    }

    fun saveTripWithImage(photo: ByteArray,trip: Trip) {
        val rand =  Random()
        val min = 1
        val max = 9999
        val randomNum = rand.nextInt(max - min + 1) + min
        trip.id = randomNum
        trip.tripPhoto.url = "@drawable/trip_photo_example"
        val imgSave = ImageCreate(ownerId = randomNum, data = photo)
        mImageCreateDao.insert(imgSave)
        trip.saved  = false
        mTripDao.insert(trip)
    }

    fun insert(trip: Trip) {
        InsertTripAsyncTask(mTripDao).execute(trip)
    }

    fun insert(event: Event) {
        val eventDao : BaseDao<Event> = when (event.eventType){
            EventType.PHOTO -> mPhotoDao as BaseDao<Event>
            EventType.TEXT -> mTextDao as BaseDao<Event>
            EventType.VIDEO -> mVideoDao as BaseDao<Event>
        }

        InsertEventAsyncTask(eventDao).execute(event)
    }

    fun getAllTrips(): LiveData<List<Trip>> {
        return mAllTrips
    }

    fun getTrip(id: Int): LiveData<Trip> {
        return mTripDao.getTrip(id.toString())
    }

    fun getFriendsEvents(lca: Fragment, callback: (MutableList<Event>)-> Unit) : Unit {
        val allTexts = mTextDao.allTexts
        val allVideos = mVideoDao.allVideos
        val allPhotos = mPhotoDao.allPhotos
        val events : MutableList<Event> = emptyArray<Event>().toMutableList()
        allTexts.observe(lca, Observer { texts ->
            if (texts != null) {
                texts.forEach {
                    txt ->
                    events.add(txt)
                }
            }
            allVideos.observe(lca, Observer { videos ->
                if (videos != null) {
                    videos.forEach {
                        vd ->
                        events.add(vd)
                    }
                }
                allPhotos.observe(lca, Observer { photos ->
                    if (photos != null) {
                        photos.forEach {
                            ph ->
                            events.add(ph)
                        }
                    }
                    callback(events)
                })
            })
        })
    }


    private class InsertTripAsyncTask internal constructor(private val mAsyncTaskDao: TripDao) : AsyncTask<Trip, Void, Void>() {

        override fun doInBackground(vararg params: Trip): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class InsertEventAsyncTask internal constructor(private val mAsyncTaskDao: BaseDao<Event>) : AsyncTask<Event, Void, Void>() {

        override fun doInBackground(vararg params: Event): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}