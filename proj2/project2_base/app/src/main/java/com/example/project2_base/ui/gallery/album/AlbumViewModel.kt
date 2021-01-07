//package com.example.project2_base.ui.gallery
//
//import android.content.Context
//import android.content.Intent
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Environment
//import android.provider.MediaStore
//import android.util.Log
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentTransaction
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import java.io.ByteArrayOutputStream
//import java.io.File
//import java.io.IOException
//import java.text.SimpleDateFormat
//import java.util.*
//import kotlin.jvm.Throws
//
//class AlbumViewModel : ViewModel() {
//
//    fun BitmapToUri(context: Context, bitmap: Bitmap): Uri {
//        var bytes =  ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG,100, bytes)
//        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
//        return Uri.parse(path.toString())
//    }
//    private fun takePicture() {
//        //카메라 앱 실행
//        var capture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(capture, REQUEST_TAKE_PHOTO)
//    }
//    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager){
//        var ft: FragmentTransaction = fragmentManager.beginTransaction()
//        ft.detach(fragment).attach(fragment).commit()
//    }
//
//    lateinit var currentPhotoPath: String
//
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
//
//    // return MediaFileData which contains id, dateTaken, displayName, uri
//    // Use MediaStore API
//    fun getFileList(context: Context, type: MediaStoreFileType): List<MediaFileData> {
//
//        val folderList = mutableListOf<MediaFileData>()
//        val projection = arrayOf(
//            MediaStore.Files.FileColumns._ID,
//            MediaStore.Files.FileColumns.DISPLAY_NAME,
//            MediaStore.Files.FileColumns.DATE_TAKEN,
//            MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
//            MediaStore.Files.FileColumns.BUCKET_ID
//        )
//
//        val sortOrder = "${MediaStore.Files.FileColumns.DATE_TAKEN} DESC"
//
//        val cursor = context.contentResolver.query(
//            type.externalContentUri,
//            projection,
//            null,
//            null,
//            sortOrder
//        )
//
//        cursor?.use {
//            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
//            val dateTakenColumn =
//                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_TAKEN)
//            val displayNameColumn =
//                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
//            val bucketIDColumn =
//                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_ID)
//            val bucketNameColumn =
//                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)
//            while (cursor.moveToNext()) {
//                val id = cursor.getLong(idColumn)
//                val dateTaken = Date(cursor.getLong(dateTakenColumn))
//                val displayName = cursor.getString(displayNameColumn)
//                val contentUri = Uri.withAppendedPath(
//                    type.externalContentUri,
//                    id.toString()
//                )
//                val bucketID = cursor.getLong(bucketIDColumn)
//                val bucketName = cursor.getString(bucketNameColumn)
//
//                Log.d(
//                    "test",
//                    "id: $id, display_name: $displayName, date_taken: $dateTaken, content_uri: $contentUri\n"
//                )
//
//                val MDF = MediaFileData(id, dateTaken, displayName, contentUri, bucketID, bucketName)
//                folderList.add(MDF)
//
//            }
//        }
//
//        return folderList
//    }
//
//    private fun <E> listContainsContitionedItem(list: MutableList<E>, item: E, condition: (E, E) -> Boolean): Boolean {
//        list.forEach { when (condition(it, item)){ true -> return true} }
//        return false
//    }
//
//
//    enum class MediaStoreFileType(
//        val externalContentUri: Uri,
//        val mimeType: String,
//        val pathByDCIM: String
//    ) {
//        IMAGE(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*", "/image"),
//        AUDIO(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "audio/*", "/audio"),
//        VIDEO(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video/*", "/video");
//    }
//}