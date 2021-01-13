package com.example.project2_base.ui.gallery.album

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project2_base.R
import com.example.project2_base.di.AlbumDatabase
import kotlinx.android.synthetic.main.fragment_album.*


class AlbumFragment : Fragment() {
  lateinit var albumViewModel: AlbumViewModel
  lateinit var albumRecyclerView: RecyclerView
  var columns: Int = 4
  val REQUEST_TAKE_PHOTO = 1
  lateinit var currentPhotoPath: String

  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {

    albumViewModel = ViewModelProvider(this).get(AlbumViewModel::class.java)
    return inflater.inflate(R.layout.fragment_album, container, false)
  }

  @RequiresApi(Build.VERSION_CODES.Q)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // RecyclerView
    var cursor= AlbumDatabase.getInstance(requireContext(), (activity as AlbumActivity).albumName)!!.cursor
    albumRecyclerView = view.findViewById(R.id.album_recycler_view)
    albumRecyclerView.layoutManager = GridLayoutManager(requireContext(), columns)
//    albumRecyclerView.adapter = AlbumCursorAdapter(requireContext(), albumViewModel.cursor!!)
    albumRecyclerView.adapter = AlbumCursorAdapter(requireContext(), cursor!!)

    // camera FAB
    camera_fab.setOnClickListener {
      //카메라 앱 실행
      var captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
      startActivityForResult(captureIntent, REQUEST_TAKE_PHOTO)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    // Update here

  }
}
