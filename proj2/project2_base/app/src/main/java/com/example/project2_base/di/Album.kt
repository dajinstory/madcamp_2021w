package com.example.project2_base.di

import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.util.*
import kotlin.properties.Delegates

class Album {
    lateinit var profileImageId: String
    lateinit var albumName: String
    var albumSize:Long = 0

    companion object {
        @RequiresApi(Build.VERSION_CODES.Q)
        fun fromCursor(cursor: Cursor?): Album {
            val imageIdColumn = cursor!!.getColumnIndexOrThrow("profileImageId")
            val albumNameColumn = cursor.getColumnIndexOrThrow("albumName")
            val albumSizeColumn = cursor.getColumnIndexOrThrow("albumSize")

            val album = Album()
            album.profileImageId = cursor.getString(imageIdColumn)
            album.albumName = cursor.getString(albumNameColumn)
            album.albumSize = cursor.getLong(albumSizeColumn)
            return album
        }
    }
}