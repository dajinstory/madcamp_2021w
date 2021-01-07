//package com.example.project2_base.ui.gallery
//
//import android.app.Activity
//import android.content.Intent
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.project2_base.R
//import com.example.project2_base.ui.phone_book.contact.ContactActivity
//import kotlinx.android.synthetic.main.fragment_phone_book.*
//
//class AlbumFragment : Fragment() {
//  lateinit var galleryViewModel: AlbumViewModel
//  lateinit var galleryRecyclerView: RecyclerView
//  val REQUEST_TAKE_PHOTO = 1
//
//  override fun onCreateView(
//    inflater: LayoutInflater,
//    container: ViewGroup?,
//    savedInstanceState: Bundle?
//  ): View? {
//
//    galleryViewModel = ViewModelProvider(this).get(AlbumViewModel::class.java)
//    return inflater.inflate(R.layout.fragment_gallery,container,false)
//  }
//
//  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//
//    // RecyclerView
//    galleryRecyclerView = view.findViewById(R.id.gallery_recycler_view)
//    galleryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//    galleryRecyclerView.adapter = GalleryCursorAdapter(requireContext(), phoneBookViewModel.cursor!!)
//
//    // FAB
//    add_fab.setOnClickListener{
//      //Toast.makeText(requireContext(), RecordDatabase.getInstance(requireContext())?.cursor?.count.toString(), Toast.LENGTH_LONG).show()
//      val nextIntent = Intent(requireContext(), ContactActivity::class.java)
//      val requestCode: Int = 10000 // ADD
//      nextIntent.putExtra("operation", "ADD")
//      nextIntent.putExtra("position", "-1")
//      startActivityForResult(nextIntent, requestCode)
//    }
//  }
//
//  override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
//    super.onActivityResult(requestCode, resultCode, data)
//
//    // Update here
//    if(resultCode == Activity.RESULT_OK) {
//      if (requestCode == REQUEST_TAKE_PHOTO) {
//        var bundle: Bundle? = data?.getExtras()
//        var bitmap: Bitmap = bundle?.get("data") as Bitmap
//        var changedUri: Uri = BitmapToUri(this.requireContext(), bitmap)
//        //ImageDataset.add(MediaFileData(changedUri))
//        //gallery.setImageBitmap(bitmap)
//      }
//      refreshFragment(this, activity?.supportFragmentManager!!)
//    }
//  }
//}