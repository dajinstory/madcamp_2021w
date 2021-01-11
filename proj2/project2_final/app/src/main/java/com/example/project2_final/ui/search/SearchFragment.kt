package com.example.project2_final.ui.search

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
import androidx.lifecycle.ViewModelProvider
import com.example.project2_final.R
import kotlinx.android.synthetic.main.fragment_search.*
import java.io.File

class SearchFragment : Fragment() {
    var searchText: String = ""
    var sortOption: String = "ASC"
    lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        return inflater.inflate(R.layout.fragment_search,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //FAB
        camera_liquor_fab.setOnClickListener{
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photoFile: File = searchViewModel.createImageFile(requireContext())!!
            val photoUri: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.project2_final.fileprovider",
                photoFile
            )
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            requireActivity().startActivityForResult(cameraIntent, 0)
        }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        // update view
    }
}