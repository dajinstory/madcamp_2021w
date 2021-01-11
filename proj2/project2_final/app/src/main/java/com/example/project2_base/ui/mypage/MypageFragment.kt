//package com.example.project2_final.ui.mypage
//
//import android.app.Activity
//import android.content.Intent
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentTransaction
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//
//class MypageFragment : Fragment() {
//  lateinit var galleryViewModel: MypageViewModel
//  lateinit var galleryRecyclerView: RecyclerView
//  var columns: Int = 4
//  val REQUEST_TAKE_PHOTO = 1
//  lateinit var currentPhotoPath: String
//
//  override fun onCreateView(
//          inflater: LayoutInflater,
//          container: ViewGroup?,
//          savedInstanceState: Bundle?
//  ): View? {
//
//    galleryViewModel = ViewModelProvider(this).get(MypageViewModel::class.java)
//    return inflater.inflate(R.layout.fragment_favorites, container, false)
//  }
//
//  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//
//    // RecyclerView
//    galleryRecyclerView = view.findViewById(R.id.gallery_recycler_view)
//    galleryRecyclerView.layoutManager = GridLayoutManager(requireContext(), columns)
//    galleryRecyclerView.adapter = GalleryCursorAdapter(requireContext(), galleryViewModel.cursor!!)
//
//    // camera FAB
//    camera_fab.setOnClickListener {
//      //카메라 앱 실행
//      var captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//      startActivityForResult(captureIntent, REQUEST_TAKE_PHOTO)
//    }
//  }
//
//  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    super.onActivityResult(requestCode, resultCode, data)
//
//    // Update here
//    if (resultCode == Activity.RESULT_OK) {
//      if (requestCode == REQUEST_TAKE_PHOTO) {
//        var bundle: Bundle? = data?.getExtras()
//        var bitmap: Bitmap = bundle?.get("data") as Bitmap
//        var changedUri: Uri = galleryViewModel.bitmap2Uri(this.requireContext(), bitmap)
//        //ImageDataset.add(MediaFileData(changedUri))
//        //gallery.setImageBitmap(bitmap)
//      }
//
//      // Refresh Fragment
//      var fragmentManager: FragmentManager = activity?.supportFragmentManager!!
//      var ft: FragmentTransaction = fragmentManager.beginTransaction()
//      ft.detach(this).attach(this).commit()
//    }
//  }
//}
