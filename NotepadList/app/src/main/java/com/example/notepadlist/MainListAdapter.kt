package com.example.notepadlist

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.mainlist_item.view.*
import kotlinx.android.synthetic.main.mainlist_item_long_dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainListAdapter(private val list: MutableList<Note>?): RecyclerView.Adapter<MainListAdapter.ListViewHolder>() {

    class ListViewHolder(val layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.mainlist_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.layout.vTextNoteListTitle.text = list!![position].title
        holder.layout.vTextNoteListContent.text = list!![position].content
        holder.layout.vTextNoteListLatest.text = list!![position].latest

        holder.layout.vLayoutNoteListItem.setOnClickListener {
            var intent = Intent(holder.layout.context, NoteWritePage::class.java)
            intent.putExtra("mode", 1)
            intent.putExtra("pos", position)
            intent.putExtra("id", list!![position].id)

            ActivityCompat.startActivityForResult(holder.layout.context as Activity, intent, 101, null)
//            holder.layout.context.startActivity(intent)
        }

        holder.layout.vLayoutNoteListItem.setOnLongClickListener {
            var layout = LayoutInflater.from(holder.layout.context).inflate(R.layout.mainlist_item_long_dialog, null)
            var build = AlertDialog.Builder(holder.layout.context).apply {
                setView(layout)
            }
            var dialog = build.create()
            dialog.show()
            layout.vTextNoteListItemDiaTitle.setText(list!![position].title)

            layout.vTextNoteListItemDiaRemove.setOnClickListener {
                runBlocking {
                    CoroutineScope(Dispatchers.IO).launch {
                        var db = AppDataBase.getInstance(holder.layout.context)
                        db?.noteDao()?.deleteNote(list[position].id)
                    }.join()
                }
                list.removeAt(position)
                notifyItemRemoved(position)

                dialog.dismiss()
            }

            true
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

}