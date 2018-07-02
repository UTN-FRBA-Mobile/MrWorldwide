package mobile.frba.utn.tpmobile.daos

import android.arch.persistence.room.*
import mobile.frba.utn.tpmobile.models.ImageCreate

@Dao
interface ImagesDao : BaseDao<ImageCreate> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertByReplacement(image: List<ImageCreate>)

    @Query("SELECT * FROM images_table")
    fun getAll(): List<ImageCreate>

    @Query("SELECT * FROM images_table WHERE ownerId = :ownerId")
    fun findByIds(ownerId: Int): List<ImageCreate>

    @Query("DELETE FROM images_table")
    fun deleteAll()

}