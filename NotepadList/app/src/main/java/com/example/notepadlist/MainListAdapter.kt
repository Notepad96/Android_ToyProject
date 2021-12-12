package com.example.notepadlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.mainlist_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainListAdapter(private val list: List<Note>?): RecyclerView.Adapter<MainListAdapter.ListViewHolder>() {

    class ListViewHolder(val layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.mainlist_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.layout.vTextNoteListTitle.text = list!![position].title
        holder.layout.vTextNoteListContent.text = list!![position].content
        holder.layout.vTextNoteListLatest.text = list!![position].latest
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

}