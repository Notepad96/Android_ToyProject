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
    var mode = 0
    var id = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_write_page)

        db = AppDataBase.getInstance(this)

        mode = intent.getIntExtra("mode", 0)
        // mode 0 = new, 1 = load
        when(mode) {
            0 -> {
                var text = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                vEditNoteWriteContent.setText(text)
            }
            1 -> {
                id = intent.getLongExtra("id", 0)
                loadNote(id)
            }
        }

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
                when(mode) {
                    0 -> saveNote()
                    1 -> updateNote()
                }
                setResult(RESULT_OK)
                finish()
            })
            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                finish()
            })
            show()
        }
    }

    private fun getLatestTime(): String {
        return SimpleDateFormat("yy/MM/dd - HH:mm:ss").format(System.currentTimeMillis())
    }

    private fun saveNote() {
        CoroutineScope(Dispatchers.IO).launch {
            var newNote = Note(vEditNoteWriteTitle.text.toString().trim(), vEditNoteWriteContent.text.toString().trim(), getLatestTime())
            db!!.noteDao().insertNote(newNote)
        }
    }

    private fun updateNote() {
        CoroutineScope(Dispatchers.IO).launch {
            var title = vEditNoteWriteTitle.text.toString().trim()
            var content = vEditNoteWriteContent.text.toString().trim()
            db!!.noteDao().updateNote(id, title, content, getLatestTime())
        }
    }

    private fun loadNote(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            var note = db!!.noteDao().getNote(id)
            vEditNoteWriteTitle.setText(note.title)
            vEditNoteWriteContent.setText(note.content)
        }
    }
}