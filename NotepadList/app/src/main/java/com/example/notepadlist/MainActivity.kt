package com.example.notepadlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mainAdapter: RecyclerView.Adapter<*>
    lateinit var mainManager: LinearLayoutManager
    var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getInstance(this)
        viewUpdate()

        mainManager = LinearLayoutManager(this)
        mainAdapter = MainListAdapter(this)

        var mainList = vListNotes.apply {
            layoutManager = mainManager
            adapter = mainAdapter
        }

        vFBtnAddNote.setOnClickListener {
            var intent = Intent(applicationContext, NoteWritePage::class.java)
            startActivity(intent)
        }
    }

    fun viewUpdate() {
        if(db!!.noteDao().getAll().isEmpty()) {
            vTxtEmptyList.visibility = View.VISIBLE
            vListNotes.visibility = View.GONE
        } else {
            vTxtEmptyList.visibility = View.GONE
            vListNotes.visibility = View.VISIBLE
        }
    }
}