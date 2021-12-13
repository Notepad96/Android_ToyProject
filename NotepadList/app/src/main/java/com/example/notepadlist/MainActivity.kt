package com.example.notepadlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    lateinit var mainAdapter: RecyclerView.Adapter<*>
    lateinit var mainManager: LinearLayoutManager
    var db: AppDataBase? = null
    var list: MutableList<Note>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDataBase.getInstance(this)
        viewUpdate()
        loadList()

        if(list != null) {
            for(note in list!!) Log.d("test", note.toString())
        }
        Log.d("test", list?.size.toString())
        mainManager = LinearLayoutManager(this)
        mainAdapter = MainListAdapter(list)

        var mainList = vListNotes.apply {
            layoutManager = mainManager
            adapter = mainAdapter
        }

        vFBtnAddNote.setOnClickListener {
            var intent = Intent(applicationContext, NoteWritePage::class.java)
            intent.putExtra("mode", 0)
//            startActivity(intent)
            startActivityForResult(intent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101) {
            if(resultCode == Activity.RESULT_OK) {
                mainAdapter.notifyItemInserted(list!!.size)
            }
        }
    }

    override fun onDestroy() {
        AppDataBase.deleteInstance()
        super.onDestroy()
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

    private fun loadList() {
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                list = db?.noteDao()?.getAll()
            }.join()
        }
    }
}