package mobile.frba.utn.tpmobile.daos

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.content.Context
import android.arch.persistence.room.Room
import android.arch.persistence.room.TypeConverters
import mobile.frba.utn.tpmobile.models.*


@Database(entities = arrayOf(Trip::class, Text::class, Photo::class, Video::class), version = 1)
@TypeConverters(WorldConverters::class)
abstract class WorldRoomDatabase : RoomDatabase() {


    abstract fun tripDao(): TripDao
    abstract fun textDao(): TextDao
    abstract fun videoDao(): VideoDao
    abstract fun photoDao(): PhotoDao

    companion object {
        private var DBINSTANCE: WorldRoomDatabase? = null

        fun getDatabase(context: Context): WorldRoomDatabase? {
            if (DBINSTANCE == null) {
                synchronized(WorldRoomDatabase::class.java) {
                    try {
                        if (DBINSTANCE == null) {
                            DBINSTANCE = Room.databaseBuilder(context.applicationContext,
                                    WorldRoomDatabase::class.java, "world_database").allowMainThreadQueries()
                                    .build()

                        }
                    } catch (e: Throwable){
                        val o = e
                    }

                }
            }
            return DBINSTANCE
        }

    }


}