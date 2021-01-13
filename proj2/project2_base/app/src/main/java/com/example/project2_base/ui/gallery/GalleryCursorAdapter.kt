package com.example.project2_base.ui.gallery

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.project2_base.R
import com.example.project2_base.di.Album
import com.example.project2_base.ui.gallery.album.AlbumActivity
import com.example.project2_base.ui.phone_book.contact.ContactActivity
import com.example.project2_base.utils.CursorRecyclerViewAdapter

class GalleryCursorAdapter(private val context : Context, cursor: Cursor)
    : CursorRecyclerViewAdapter<GalleryCursorAdapter.ViewHolder?>(context, cursor) {

    class ViewHolder constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        val _view: View
        val albumImage: ImageView
        val albumName: TextView
        val albumSize: TextView

        init {
            _view = view
            albumImage = view.findViewById(R.id.album_image)
            albumName = view.findViewById(R.id.album_name)
            albumSize = view.findViewById(R.id.album_size)
        }
        fun setItem(item: Album, position: Int) {

            albumName.text = item?.albumName ?: "Album"
            albumSize.text = item?.albumSize.toString() ?: "1"
            itemView.setOnClickListener {
                val albumIntent = Intent(_view.context, AlbumActivity::class.java)
                albumIntent.putExtra("albumName", item!!.albumName)
                val requestCode: Int = 0 // NOTHING
                (_view.context as Activity).startActivityForResult(albumIntent, requestCode)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(viewHolder: ViewHolder?, cursor: Cursor?) {
        val album: Album = Album.fromCursor(cursor)
        viewHolder!!.setItem(album, cursor!!.position)
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }


}