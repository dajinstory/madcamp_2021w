package com.example.project2_base.di

import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import java.util.*
import kotlin.properties.Delegates

class Image {
    var id by Delegates.notNull<Long>()
    lateinit var dateTaken: Date
    lateinit var displayName: String
    lateinit var uri: Uri
    var bucketId by Delegates.notNull<Long>()
    lateinit var bucketName: String

    companion object {
        fun fromCursor(cursor: Cursor?): Image {
            val idColumn = cursor!!.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val dateTakenColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_TAKEN)
            val displayNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val bucketIDColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_ID)
            val bucketNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)

            val image = Image()
            image.id = cursor.getLong(idColumn)
            image.dateTaken = Date(cursor.getLong(dateTakenColumn))
            image.displayName = cursor.getString(displayNameColumn)
            image.uri = Uri.withAppendedPath(
                MediaStoreFileType.IMAGE.externalContentUri,
                image.id.toString()
            )
            image.bucketId = cursor.getLong(bucketIDColumn)
            image.bucketName = cursor.getString(bucketNameColumn)
            return image
        }
    }
}