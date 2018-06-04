package mobile.frba.utn.tpmobile.daos

import android.arch.lifecycle.LiveData
import mobile.frba.utn.tpmobile.models.Word
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


@Dao
interface WordDao {

    @get:Query("SELECT * from word_table ORDER BY word ASC")
    val allWords: LiveData<List<Word>>

    @Insert
    fun insert(word: Word)

    @Query("DELETE FROM word_table")
    fun deleteAll()
}