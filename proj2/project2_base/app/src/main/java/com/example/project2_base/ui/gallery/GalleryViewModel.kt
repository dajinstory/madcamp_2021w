package com.example.project2_base.ui.gallery

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.ViewModel
import com.example.project2_base.di.GalleryDatabase
import com.example.project2_base.di.PhoneBookDatabase
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

class GalleryViewModel(
        application : Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    var galleryDatabase: GalleryDatabase? = GalleryDatabase.getInstance(context)
    var cursor: Cursor? = galleryDatabase?.cursor
    fun BitmapToUri(context: Context, bitmap: Bitmap): Uri {
        var bytes =  ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }

//    @Throws(IOException::class)
//    private fun createImageFile(): File {
//        // Create an image file name
//        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val storageDir: File = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
//        return File.createTempFile(
//            "JPEG_${timeStamp}_", /* prefix */
//            ".jpg", /* suffix */
//            storageDir /* directory */
//        ).apply {
//            // Save a file: path for use with ACTION_VIEW intents
//            currentPhotoPath = absolutePath
//        }
//    }

}