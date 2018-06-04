package mobile.frba.utn.tpmobile.daos

import mobile.frba.utn.tpmobile.models.Word
import android.os.AsyncTask
import android.arch.lifecycle.LiveData
import android.app.Application




class WordRepository internal constructor(application: Application) {

    private val mWordDao: WordDao
    internal val mAllWords: LiveData<List<Word>>

    init {
        val db = WordRoomDatabase.getDatabase(application)
        mWordDao = db!!.wordDao()
        mAllWords = mWordDao.allWords
    }

    fun insert(word: Word) {
        insertAsyncTask(mWordDao).execute(word)
    }

    fun getAllWords(): LiveData<List<Word>> {
        return mAllWords
    }


    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: WordDao) : AsyncTask<Word, Void, Void>() {

        override fun doInBackground(vararg params: Word): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}