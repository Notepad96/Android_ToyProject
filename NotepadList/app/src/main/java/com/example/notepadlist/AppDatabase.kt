package com.example.notepadlist

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = arrayOf(Note::class), version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase? {
            if(instance == null) {
                synchronized(AppDataBase::class) {
                    instance = Room.databaseBuilder(context, AppDataBase::class.java, "noteDB").build()
                }
            }
            return instance
        }

        fun deleteInstance() {
            instance = null
        }
    }

}