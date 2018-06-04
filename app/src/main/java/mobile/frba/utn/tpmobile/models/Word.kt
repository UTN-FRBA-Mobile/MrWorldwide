package mobile.frba.utn.tpmobile.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.ColumnInfo
import android.support.annotation.NonNull


@Entity(tableName = "word_table")
class Word(@field:PrimaryKey
           @field:ColumnInfo(name = "word")
           val word: String)