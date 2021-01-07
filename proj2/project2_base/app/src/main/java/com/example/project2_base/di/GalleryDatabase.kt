package com.example.project2_base.di

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.util.*

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

        fun getInstance(context: Context): GalleryDatabase? {
            if (INSTANCE == null) {
                // Cursor
                val uri = MediaStoreFileType.IMAGE.externalContentUri
                val projection = arrayOf(
                    MediaStore.Files.FileColumns._ID,
                    MediaStore.Files.FileColumns.DISPLAY_NAME,
                    MediaStore.Files.FileColumns.DATE_TAKEN,
                    MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
                    MediaStore.Files.FileColumns.BUCKET_ID
                )
                val selectionClause = null
                val selectionArgs = emptyArray<String>()
                val sortOrder = "${MediaStore.Files.FileColumns.DATE_TAKEN} DESC"
                val cursor = context?.contentResolver?.query(
                    uri,
                    projection,
                    selectionClause,
                    selectionArgs,
                    sortOrder
                )
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
    }
}
