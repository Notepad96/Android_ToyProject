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

    @Query("UPDATE notes SET title = :title WHERE id = :id")
    fun updateTitle(id: Long, title: String)

    @Query("UPDATE notes SET content = :content WHERE id = :id")
    fun updateContent(id: Long, content: String)

    @Query("UPDATE notes SET latest = :latest WHERE id = :id")
    fun updateLatest(id: Long, latest: Int)

    @Query("SELECT * FROM notes")
    fun getAll(): List<Note>
}