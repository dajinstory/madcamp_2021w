package com.example.project2_base.ui.gallery

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel

import com.example.project2_base.di.GalleryDatabase
import java.io.ByteArrayOutputStream

class GalleryViewModel(
        application : Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    @RequiresApi(Build.VERSION_CODES.Q)
    var galleryDatabase: GalleryDatabase? = GalleryDatabase.getInstance(context)
    var cursor: Cursor? = galleryDatabase?.cursor
    fun bitmap2Uri(context: Context, bitmap: Bitmap): Uri {
        var bytes =  ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }


}