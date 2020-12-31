package com.example.proj1_tablayout.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proj1_tablayout.R
import com.example.proj1_tablayout.adapter.GalleryAdapter
import com.example.proj1_tablayout.adapter.InGalleryImageAdapter
import com.example.proj1_tablayout.model.MediaFileData
import kotlinx.android.synthetic.main.galleryfragment_tab.view.*
import java.util.*


class GalleryFragmentTab : Fragment() {
    var name = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.galleryfragment_tab,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ImageDataset = getFileList(requireContext(), MediaStoreFileType.IMAGE)

        val folderDataset = mutableListOf<MediaFileData>()

        val condition:(MediaFileData,MediaFileData)->Boolean= { mdf1: MediaFileData, mdf2: MediaFileData -> mdf1.bucketId == mdf2.bucketId }

        ImageDataset.forEach{ if (listContainsContitionedItem(folderDataset, it, condition).not()) folderDataset.add(it) }


        val recyclerView: RecyclerView = view.gallery
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = GalleryAdapter(requireContext(), folderDataset)

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

    fun <E> listContainsContitionedItem(list: MutableList<E>, item: E, condition: (E, E) -> Boolean): Boolean {
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