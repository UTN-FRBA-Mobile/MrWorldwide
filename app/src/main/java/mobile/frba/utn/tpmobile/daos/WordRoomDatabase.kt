package mobile.frba.utn.tpmobile.daos

import android.arch.persistence.room.RoomDatabase
import mobile.frba.utn.tpmobile.models.Word
import android.arch.persistence.room.Database
import android.content.Context
import android.arch.persistence.room.Room




@Database(entities = arrayOf(Word::class), version = 1)
abstract class WordRoomDatabase : RoomDatabase() {


    abstract fun wordDao(): WordDao

    companion object {
        private var DBINSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context): WordRoomDatabase? {
            if (DBINSTANCE == null) {
                synchronized(WordRoomDatabase::class.java) {
                    try {
                        if (DBINSTANCE == null) {
                            DBINSTANCE = Room.databaseBuilder(context.applicationContext,
                                    WordRoomDatabase::class.java, "word_database")
                                    .build()

                        }
                    } catch (e: Exception){
                        val o = e
                    }

                }
            }
            return DBINSTANCE
        }

    }


}