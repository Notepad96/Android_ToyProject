package com.example.notepadlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MainListAdapter(context: Context): RecyclerView.Adapter<MainListAdapter.ListViewHolder>() {
    var db: AppDatabase? = AppDatabase.getInstance(context)
    var list = db?.noteDao()?.getAll()

    class ListViewHolder(layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.mainlist_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}