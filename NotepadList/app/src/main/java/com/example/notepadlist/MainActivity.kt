package com.example.notepadlist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
    lateinit var mainList: RecyclerView
    var db: AppDataBase? = null
    var list: MutableList<Note>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDataBase.getInstance(this)
        viewUpdate()
        loadList()

        mainManager = LinearLayoutManager(this)
        mainAdapter = MainListAdapter(list)

        mainList = vListNotes.apply {
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
                Log.d("test", "In check")
                val note = data?.getParcelableExtra<Note>("note")
                note!!.id = data?.getLongExtra("id", 0L)
                // mode 0 = insert, 1 = update
                when(data?.getIntExtra("mode", 0)) {
                    0 -> {
                        list?.add(note!!)
                        mainAdapter.notifyDataSetChanged()
                        mainList.invalidate()
                        viewUpdate()
                    }
                    1 -> {
                        val pos = data.getIntExtra("pos", 0)

                        list!![pos].title = note!!.title
                        list!![pos].content = note.content
                        list!![pos].latest = note.latest
                        mainAdapter.notifyItemChanged(pos)
                    }
                }


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

    override fun onStart() {
        viewUpdate()
        super.onStart()
    }
}

