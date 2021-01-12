package com.example.project2_server.ui.favorites

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
import com.example.project2_server.ui.home.ShopCursorAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.File

class FavoritesFragment : Fragment() {
    lateinit var favoritesViewModel: FavoritesViewModel
    lateinit var liquorRecyclerView: RecyclerView
    val REQUEST_TAKE_PHOTO = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        favoritesViewModel.initRetrofit()
        return inflater.inflate(R.layout.fragment_favorites,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Liquor RecyclerView
        liquorRecyclerView = view.findViewById(R.id.favorite_recycler_view)
        liquorRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        liquorRecyclerView.adapter = LiquorCursorAdapter(requireContext(), favoritesViewModel.getLiquorCursor())

        //FAB
        camera_add_fab.setOnClickListener{
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photoFile: File = favoritesViewModel.createImageFile(requireContext())!!
            val photoUri: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.project2_server.fileprovider",
                photoFile
            )
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO)
        }

        // FAB
        reset_fab.setOnClickListener{
            favoritesViewModel.syncLiquorList()
            liquorRecyclerView.adapter = LiquorCursorAdapter(requireContext(), favoritesViewModel.getLiquorCursor())
        }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if(requestCode == REQUEST_TAKE_PHOTO){
            val file = favoritesViewModel.currentPhotoPath
            val uri = Uri.fromFile(File(favoritesViewModel.currentPhotoPath))

            favoritesViewModel.uploadImage(file, uri)

            // update view
            favoritesViewModel.syncLiquorList()
            liquorRecyclerView.adapter = LiquorCursorAdapter(requireContext(), favoritesViewModel.getLiquorCursor())
        }
    }
    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager){
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.detach(fragment).attach(fragment).commit()
    }
}