package mobile.frba.utn.tpmobile.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import mobile.frba.utn.tpmobile.models.Text


@Dao
interface TextDao : BaseDao<Text>{

    @get:Query("SELECT * from text_table ORDER BY id ASC")
    val allTexts: LiveData<List<Text>>

    @get:Query("SELECT * from text_table ORDER BY id ASC")
    val loadTextsSync: List<Text>

    @Query("SELECT * FROM text_table WHERE id = :id ")
    fun getText(id: String): LiveData<Text>

    @Query("DELETE FROM text_table")
    fun deleteAll()
}