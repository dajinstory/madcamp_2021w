package com.example.project2_base.di

import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi

class GalleryDatabase {
    var cursor: Cursor? = null
    var uri: Uri? = null
    var projection: Array<String>? = null
    var selectionClause: String? = null
    var selectionArgs: Array<String>? = null
    var sortOrder: String? = null

    companion object {
        var INSTANCE: GalleryDatabase? = null
        val type: MediaStoreFileType = MediaStoreFileType.IMAGE

        @RequiresApi(Build.VERSION_CODES.Q)
        fun getInstance(context: Context): GalleryDatabase? {
            if (INSTANCE == null) {
                // Cursor
                val uri = MediaStoreFileType.IMAGE.externalContentUri
                val projection = arrayOf(
                        MediaStore.Files.FileColumns._ID,
                        MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME
                )
                val selectionClause = null
                val selectionArgs = emptyArray<String>()
                val sortOrder = "${MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME} ASC"
                var cursor = context?.contentResolver?.query(
                        uri,
                        projection,
                        selectionClause,
                        selectionArgs,
                        sortOrder
                )

                cursor = filterCursor(cursor!!)

                INSTANCE = GalleryDatabase()
                INSTANCE!!.cursor = cursor
                INSTANCE!!.uri = uri
                INSTANCE!!.projection = projection
                INSTANCE!!.selectionClause = selectionClause
                INSTANCE!!.selectionArgs = selectionArgs
                INSTANCE!!.sortOrder = sortOrder
            }
            return INSTANCE
        }

        @RequiresApi(Build.VERSION_CODES.Q)
        fun filterCursor(cursor: Cursor): Cursor{
            var albumList = arrayListOf<Album>()
            var albumName: String = ""
            cursor?.use {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                val bucketNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)
                while (cursor.moveToNext()) {
                    val _id = cursor.getString(idColumn)
                    val bucketName = cursor.getString(bucketNameColumn)

                    if(albumName != bucketName){
                        val newAlbum = Album()
                        newAlbum.profileImageId = _id
                        newAlbum.albumName = bucketName
                        newAlbum.albumSize = 1
                        albumList.add(newAlbum)
                        albumName = bucketName
                    }else{
                        albumList[albumList.size-1].albumSize += 1
                    }
                }
            }
            var albumCursor = MatrixCursor(arrayOf("profileImageId","albumName","albumSize"))
            for (album in albumList) {
                albumCursor.newRow()
                        .add("profileImageId", album.profileImageId)
                        .add("albumName", album.albumName)
                        .add("albumSize", album.albumSize)
            }
            return (albumCursor as Cursor)
        }
    }
}
