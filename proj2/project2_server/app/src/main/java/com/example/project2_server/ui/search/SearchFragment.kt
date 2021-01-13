package com.example.project2_server.ui.search

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project2_server.R
import com.example.project2_server.ui.home.LiquorCursorAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import java.io.File


class SearchFragment : Fragment() {
    lateinit var searchViewModel: SearchViewModel
    lateinit var liquorGroupRecyclerView: RecyclerView
    val REQUEST_TAKE_PHOTO = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.initRetrofit()
        return inflater.inflate(R.layout.fragment_search,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Liquor RecyclerView
        liquorGroupRecyclerView = view.findViewById(R.id.liquor_group_recycler_view)
        liquorGroupRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        liquorGroupRecyclerView.adapter = LiquorCursorAdapter(requireContext(), searchViewModel.getLiquorCursor())

        //FAB
        camera_search_fab.setOnClickListener{
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photoFile: File = searchViewModel.createImageFile(requireContext())!!
            val photoUri: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.project2_server.fileprovider",
                    photoFile
            )
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO)
        }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if(requestCode == REQUEST_TAKE_PHOTO){
            val file = searchViewModel.currentPhotoPath
            val uri = Uri.fromFile(File(searchViewModel.currentPhotoPath))

            searchViewModel.uploadImage(file, uri)
            //var changedUri: Uri = BitmapToUri(this.requireContext(), bitmap)
            //ImageDataset.add(MediaFileData(changedUri))
            //gallery.setImageBitmap(bitmap)
        }

    }
    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager){
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.detach(fragment).attach(fragment).commit()
    }
}