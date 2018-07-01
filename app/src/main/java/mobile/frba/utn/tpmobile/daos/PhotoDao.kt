package mobile.frba.utn.tpmobile.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import mobile.frba.utn.tpmobile.models.Photo


@Dao
interface PhotoDao : BaseDao<Photo>{

    @get:Query("SELECT * from photo_table ORDER BY id ASC")
    val allPhotos: LiveData<List<Photo>>

    @get:Query("SELECT * from photo_table ORDER BY id ASC")
    val loadPhotosSync: List<Photo>

    @Query("SELECT * FROM photo_table WHERE id = :id ")
    fun getPhoto(id: String): LiveData<Photo>

    @Query("DELETE FROM photo_table")
    fun deleteAll()
}