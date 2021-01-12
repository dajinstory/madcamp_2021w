package com.example.project2_final.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project2_final.R

class HomeFragment : Fragment() {
  var searchText: String = ""
  var sortOption: String = "ASC"
  lateinit var homeViewModel: HomeViewModel
  lateinit var liquorRecyclerView: RecyclerView
  lateinit var shopRecyclerView: RecyclerView

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    return inflater.inflate(R.layout.fragment_home,container,false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Load from Server
    homeViewModel.loadFromServer(requireContext())

    // Liquor RecyclerView
    liquorRecyclerView = view.findViewById(R.id.best_liquor_recycler_view)
    liquorRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    liquorRecyclerView.adapter = BestLiquorCursorAdapter(requireContext(), homeViewModel.getBestLiquorCursor())

    // Shop RecyclerView
    shopRecyclerView = view.findViewById(R.id.best_shop_recycler_view)
    shopRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    shopRecyclerView.adapter = BestShopCursorAdapter(requireContext(), homeViewModel.getBestShopCursor())

    // FAB
//    add_shop_fab.setOnClickListener{
//      val addShopIntent = Intent(requireContext(), AddShopActivity::class.java)
//      startActivityForResult(addShopIntent, 0)
//    }
  }

  override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (resultCode != Activity.RESULT_OK){
      return
    }

    // update view
    liquorRecyclerView.adapter = BestLiquorCursorAdapter(requireContext(), homeViewModel.getBestLiquorCursor())
    shopRecyclerView.adapter = BestShopCursorAdapter(requireContext(), homeViewModel.getBestShopCursor())
  }
}