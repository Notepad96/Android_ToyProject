package com.example.notepadlist

import androidx.room.*

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

//    @Query("UPDATE notes SET title = :title, content = :content, latest = :latest WHERE id = :id")
//    fun updateNote(id: Long, title: String, content: String, latest: String)

    @Update
    fun updateNote(note: Note)

    @Query("UPDATE notes SET title = :title WHERE id = :id")
    fun updateTitle(id: Long, title: String)

    @Query("UPDATE notes SET content = :content WHERE id = :id")
    fun updateContent(id: Long, content: String)

    @Query("UPDATE notes SET latest = :latest WHERE id = :id")
    fun updateLatest(id: Long, latest: Int)

    @Query("SELECT * FROM notes where id = :id")
    fun getNoteID(id: Long): Note

    @Query("SELECT * FROM notes where latest = :latest")
    fun getNoteLatest(latest: String): Note

    @Query("SELECT * FROM notes")
    fun getAll(): MutableList<Note>
}