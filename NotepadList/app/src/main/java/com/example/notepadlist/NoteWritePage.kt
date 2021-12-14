package com.example.notepadlist

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_note_write_page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat

class NoteWritePage : AppCompatActivity() {
    var db: AppDataBase? = null
    var mode = 0
    var id = 0L
    var pos = 0

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
                pos = intent.getIntExtra("pos", 0)
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
                var note = when(mode) {
                    0 -> saveNote()
                    1 -> updateNote()
                    else -> null
                }
                val intent = Intent()
                intent.putExtra("mode", mode)
                intent.putExtra("pos", pos)
                intent.putExtra("note", note)
                intent.putExtra("id", note!!.id)
                setResult(RESULT_OK, intent)
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

    private fun saveNote(): Note {
        var latest = getLatestTime()
        var newNote = Note(vEditNoteWriteTitle.text.toString().trim(), vEditNoteWriteContent.text.toString().trim(), latest)
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                db!!.noteDao().insertNote(newNote)
                newNote.id = db!!.noteDao().getNoteLatest(latest).id
            }.join()
        }

        return newNote
    }

    private fun updateNote(): Note {
        var title = vEditNoteWriteTitle.text.toString().trim()
        var content = vEditNoteWriteContent.text.toString().trim()
        var changeNote = Note(title, content, getLatestTime())
        changeNote.id = id
        CoroutineScope(Dispatchers.IO).launch {
            db!!.noteDao().updateNote(changeNote)
        }

        return changeNote
    }

    private fun loadNote(id: Long) {
        lateinit var note: Note
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                note = db!!.noteDao().getNoteID(id)
            }.join()
        }
        vEditNoteWriteTitle.setText(note.title)
        vEditNoteWriteContent.setText(note.content)
    }
}