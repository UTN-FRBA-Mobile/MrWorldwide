package mobile.frba.utn.tpmobile.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "images_table")
data class ImageCreate (@PrimaryKey(autoGenerate = true) var id: Int = 1, var ownerId : Int, @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var data: ByteArray? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageCreate

        if (id != other.id) return false
        if (ownerId != other.ownerId) return false
        if (!Arrays.equals(data, other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + ownerId
        result = 31 * result + (data?.let { Arrays.hashCode(it) } ?: 0)
        return result
    }
}
