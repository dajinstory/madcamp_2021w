package com.example.proj1_tablayout.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proj1_tablayout.R
import com.example.proj1_tablayout.adapter.GalleryAdapter
import com.example.proj1_tablayout.model.MediaFileData
import kotlinx.android.synthetic.main.galleryfragment_tab.*
import kotlinx.android.synthetic.main.galleryfragment_tab.view.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class GalleryFragmentTab : Fragment() {
    var name = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.galleryfragment_tab,container,false)
    }

    lateinit var ImageDataset: MutableList<MediaFileData>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ImageDataset = getFileList(requireContext(), MediaStoreFileType.IMAGE) as MutableList<MediaFileData>

        val folderDataset = mutableListOf<MediaFileData>()
        val countImages = mutableListOf<Int>()
        val folderId = mutableListOf<Long>()

        val condition:(MediaFileData,MediaFileData)->Boolean= { mdf1: MediaFileData, mdf2: MediaFileData -> mdf1.bucketId == mdf2.bucketId }

        ImageDataset.forEach{
            if (listContainsContitionedItem(folderDataset, it, condition).not()) {
                folderDataset.add(it)
                countImages.add(1)
                folderId.add(it.bucketId)
            }
            else{
                countImages[folderId.indexOf(it.bucketId)] += 1
            }
        }



        camerafab.setOnClickListener {
            takePicture()
        }

        val recyclerView: RecyclerView = view.gallery
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = GalleryAdapter(requireContext(), folderDataset, countImages)


    }


    val REQUEST_TAKE_PHOTO = 1

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK) {

            if (requestCode == REQUEST_TAKE_PHOTO) {
                var bundle: Bundle? = data?.getExtras()
                var bitmap: Bitmap = bundle?.get("data") as Bitmap
                var changedUri: Uri = BitmapToUri(this.requireContext(), bitmap)
                //ImageDataset.add(MediaFileData(changedUri))
                //gallery.setImageBitmap(bitmap)
            }
            refreshFragment(this, activity!!.supportFragmentManager)
        }
    }
    fun BitmapToUri(context: Context, bitmap: Bitmap): Uri {
        var bytes =  ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }
    private fun takePicture() {
        //카메라 앱 실행
        var capture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(capture, REQUEST_TAKE_PHOTO)
    }
    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager){
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.detach(fragment).attach(fragment).commit()
    }

    // return MediaFileData which contains id, dateTaken, displayName, uri
    // Use MediaStore API
    fun getFileList(context: Context, type: MediaStoreFileType): List<MediaFileData> {

        val folderList = mutableListOf<MediaFileData>()
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_TAKEN,
            MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Files.FileColumns.BUCKET_ID
        )

        val sortOrder = "${MediaStore.Files.FileColumns.DATE_TAKEN} DESC"

        val cursor = context.contentResolver.query(
            type.externalContentUri,
            projection,
            null,
            null,
            sortOrder
        )

        cursor?.use {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val dateTakenColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_TAKEN)
            val displayNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val bucketIDColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_ID)
            val bucketNameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val dateTaken = Date(cursor.getLong(dateTakenColumn))
                val displayName = cursor.getString(displayNameColumn)
                val contentUri = Uri.withAppendedPath(
                    type.externalContentUri,
                    id.toString()
                )
                val bucketID = cursor.getLong(bucketIDColumn)
                val bucketName = cursor.getString(bucketNameColumn)

                Log.d(
                    "test",
                    "id: $id, display_name: $displayName, date_taken: $dateTaken, content_uri: $contentUri\n"
                )

                val MDF = MediaFileData(id, dateTaken, displayName, contentUri, bucketID, bucketName)
                folderList.add(MDF)

            }
        }

        return folderList
    }

    private fun <E> listContainsContitionedItem(list: MutableList<E>, item: E, condition: (E, E) -> Boolean): Boolean {
        list.forEach { when (condition(it, item)){ true -> return true} }
        return false
    }


    enum class MediaStoreFileType(
        val externalContentUri: Uri,
        val mimeType: String,
        val pathByDCIM: String
    ) {
        IMAGE(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*", "/image"),
        AUDIO(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "audio/*", "/audio"),
        VIDEO(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video/*", "/video");
    }

}