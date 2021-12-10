package com.example.notepadlist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {


    @Insert
    fun insertNote(note: Note)

//    @Query("INSERT INTO notes(title, content, latest) VALUES (:title, :content, :latest)")
//    fun insertNote(note: Note)

    @Query("DELETE FROM notes where id = :id")
    fun deleteNote(id: Long)

    @Query("DELETE FROM notes")
    fun deleteAll()
}