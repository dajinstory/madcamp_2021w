package com.example.project2_server.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project2_server.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
  lateinit var homeViewModel: HomeViewModel
  lateinit var liquorRecyclerView: RecyclerView
  lateinit var shopRecyclerView: RecyclerView

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    homeViewModel.initRetrofit()
    return inflater.inflate(R.layout.fragment_home,container,false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Liquor RecyclerView
    liquorRecyclerView = view.findViewById(R.id.best_liquor_recycler_view)
    liquorRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
    liquorRecyclerView.adapter = LiquorCursorAdapter(requireContext(), homeViewModel.getLiquorCursor())

    // Shop RecyclerView
    shopRecyclerView = view.findViewById(R.id.best_shop_recycler_view)
    shopRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
    shopRecyclerView.adapter = ShopCursorAdapter(requireContext(), homeViewModel.getShopCursor())

    // FAB
    add_shop_fab.setOnClickListener{
      homeViewModel.loadShopList()
      homeViewModel.loadLiquorList()
      liquorRecyclerView.adapter = LiquorCursorAdapter(requireContext(), homeViewModel.getLiquorCursor())
      shopRecyclerView.adapter = ShopCursorAdapter(requireContext(), homeViewModel.getShopCursor())
    }
  }

  override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode != Activity.RESULT_OK){
      return
    }

    // update view
    liquorRecyclerView.adapter = LiquorCursorAdapter(requireContext(), homeViewModel.getLiquorCursor())
    shopRecyclerView.adapter = ShopCursorAdapter(requireContext(), homeViewModel.getShopCursor())
    refreshFragment(this, activity?.supportFragmentManager!!)
  }

  fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager){
    var ft: FragmentTransaction = fragmentManager.beginTransaction()
    ft.detach(fragment).attach(fragment).commit()
  }
}