package com.example.project2_final.ui.mypage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project2_final.R

class MypageFragment : Fragment() {
    var searchText: String = ""
    var sortOption: String = "ASC"
    lateinit var mypageViewModel: MypageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mypageViewModel = ViewModelProvider(this).get(MypageViewModel::class.java)
        return inflater.inflate(R.layout.fragment_mypage,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // FAB
//    add_shop_fab.setOnClickListener{
//      val addShopIntent = Intent(requireContext(), AddShopActivity::class.java)
//      startActivityForResult(addShopIntent, 0)
//    }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        // update view
    }
}