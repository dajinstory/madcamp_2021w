package com.example.project2_base.ui.gallery

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project2_base.R
import com.example.project2_base.di.Contact
import com.example.project2_base.di.Image
import com.example.project2_base.ui.phone_book.contact.ContactActivity
import com.example.project2_base.utils.CursorRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_contact.view.*
import java.util.*
import kotlin.properties.Delegates


class GalleryCursorAdapter(private val context : Context, cursor: Cursor)
    : CursorRecyclerViewAdapter<GalleryCursorAdapter.ViewHolder?>(context, cursor) {

    class ViewHolder constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        val _view: View
        val image: ImageView
        val name: TextView
        val numbers: TextView

        init {
            _view = view
            image = view.findViewById(R.id.album_image)
            name = view.findViewById(R.id.album_name)
            numbers = view.findViewById(R.id.album_size)
        }
        fun setItem(item: Image, position: Int) {

            name.text = item?.bucketName ?: "Album"
            numbers.text = "1"
            itemView.setOnClickListener {
                val albumIntent = Intent(_view.context, ContactActivity::class.java)
                val requestCode: Int = 0 // NOTHING
                (_view.context as Activity).startActivityForResult(albumIntent, requestCode)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, cursor: Cursor?) {
        val album: Image = Image.fromCursor(cursor)
        viewHolder!!.setItem(album, cursor!!.position)
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }


}