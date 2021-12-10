package com.example.notepadlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    var title: String = "",
    var content: String = "",
    var latest: String = ""
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    override fun toString(): String {
        return "$title, $content, $latest"
    }
}