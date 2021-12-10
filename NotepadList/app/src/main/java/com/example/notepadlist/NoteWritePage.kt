package com.example.notepadlist

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_note_write_page.*

class NoteWritePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_write_page)

        vBtnNoteWriteExit.setOnClickListener {
            exitDialog()
        }
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
                finish()
            })
            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                finish()
            })
            show()
        }
    }
}