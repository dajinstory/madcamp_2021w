package com.example.project1_final.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project1_final.R
import com.example.project1_final.model.Record


class RecordCursorAdapter(private val context : Context, cursor: Cursor)
    :CursorRecyclerViewAdapter<RecordCursorAdapter.ViewHolder?>(context, cursor) {

    class ViewHolder constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        val _view: View
        val name: TextView
        val time: TextView
        val score: TextView

        fun setItem(item: Record, position: Int) {
            // image.?? = item?.image ?: "default.png"
            name.text = item?.name ?: "None"
            time.text = item?.name ?: "None"
            score.text = item?.name ?: "None"
        }

        init {
            _view = view
            name = view.findViewById(R.id.record_name)
            time = view.findViewById(R.id.record_time)
            score = view.findViewById(R.id.record_score)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, cursor: Cursor?) {
        val record: Record = Record.fromCursor(cursor)
        viewHolder!!.setItem(record, cursor!!.position)
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }


}