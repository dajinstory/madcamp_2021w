package com.example.project2_base.ui.gallery.album

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project2_base.R
import com.example.project2_base.di.Image
import com.example.project2_base.utils.CursorRecyclerViewAdapter


class AlbumCursorAdapter(private val context : Context, cursor: Cursor)
    : CursorRecyclerViewAdapter<AlbumCursorAdapter.ViewHolder?>(context, cursor) {

    class ViewHolder constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        val _view: View

        init {
            _view = view
        }

        fun setItem(item: Image, position: Int) {
            itemView.setOnClickListener {
                val nextIntent = Intent(_view.context, AlbumActivity::class.java)
                val requestCode: Int = 0 // NOTHING
                (_view.context as Activity).startActivityForResult(nextIntent, requestCode)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, cursor: Cursor?) {
        val image: Image = Image.fromCursor(cursor)
        viewHolder!!.setItem(image, cursor!!.position)
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }


}