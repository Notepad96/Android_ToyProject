package com.example.notepadlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mainAdapter: RecyclerView.Adapter<*>
    lateinit var mainManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainManager = LinearLayoutManager(this)
        mainAdapter = MainListAdapter()

        var mainList = vMainList.apply {
            layoutManager = mainManager
            adapter = mainAdapter
        }
    }
}