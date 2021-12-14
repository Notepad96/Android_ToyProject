package com.example.notepadlist

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "notes")
data class Note(
    var title: String = "",
    var content: String = "",
    var latest: String = ""
) : Parcelable
{
    @PrimaryKey(autoGenerate = true) var id: Long = 0L

    override fun toString(): String {
        return "$id: $title, $content, $latest"
    }
}