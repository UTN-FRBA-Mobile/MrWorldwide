package mobile.frba.utn.tpmobile.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import mobile.frba.utn.tpmobile.models.Video


@Dao
interface VideoDao : BaseDao<Video>{

    @get:Query("SELECT * from video_table ORDER BY id ASC")
    val allVideos: LiveData<List<Video>>

    @get:Query("SELECT * from video_table ORDER BY id ASC")
    val loadVideosSync: List<Video>

    @Query("SELECT * FROM video_table WHERE id = :id ")
    fun getVideo(id: String): LiveData<Video>

    @Query("DELETE FROM video_table")
    fun deleteAll()
}