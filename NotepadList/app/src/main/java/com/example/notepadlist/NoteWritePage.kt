package com.example.notepadlist

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_note_write_page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class NoteWritePage : AppCompatActivity() {
    var db: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_write_page)

        db = AppDataBase.getInstance(this)

        vBtnNoteWriteExit.setOnClickListener {
            exitDialog()
        }
    }

    override fun onDestroy() {
        AppDataBase.deleteInstance()
        super.onDestroy()
    }
    override fun onBackPressed() {
        exitDialog()
    }

    private fun exitDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("저 장")
            setMessage("변경된 내용을 저장하시겠습니까?")
            setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(applicationContext, "저장 되었습니다!", Toast.LENGTH_LONG).show()
                saveNote()
                finish()
            })
            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                finish()
            })
            show()
        }
    }

    private fun saveNote() {
        CoroutineScope(Dispatchers.IO).launch {
            var time = SimpleDateFormat("yy/MM/dd - HH:mm:ss").format(System.currentTimeMillis())
            var newNote = Note(vEditNoteWriteTitle.text.toString().trim(), vEditNoteWriteContent.text.toString().trim(), time)
            db!!.noteDao().insertNote(newNote)
        }
    }
}