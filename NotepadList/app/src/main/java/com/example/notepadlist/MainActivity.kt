package com.example.notepadlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var mainAdapter: RecyclerView.Adapter<*>
    lateinit var mainManager: LinearLayoutManager
    var db: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDataBase.getInstance(this)
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

    private fun viewUpdate() {
        CoroutineScope(Dispatchers.IO).launch {
            if(db!!.noteDao().getAll().isEmpty()) {
                vTextEmptyList.visibility = View.VISIBLE
                vListNotes.visibility = View.GONE
            } else {
                vTextEmptyList.visibility = View.GONE
                vListNotes.visibility = View.VISIBLE
            }
        }
    }
}