//package com.example.project2_final.ui.favorites
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import com.example.project2_base.R
//
//class FavoritesFragment : Fragment() {
//
//  private lateinit var gameViewModel: FavoritesViewModel
//
//  override fun onCreateView(
//    inflater: LayoutInflater,
//    container: ViewGroup?,
//    savedInstanceState: Bundle?
//  ): View? {
//    gameViewModel =
//            ViewModelProvider(this).get(FavoritesViewModel::class.java)
//    val root = inflater.inflate(R.layout.fragment_search, container, false)
//    val textView: TextView = root.findViewById(R.id.text_notifications)
//    gameViewModel.text.observe(viewLifecycleOwner, Observer {
//      textView.text = it
//    })
//    return root
//  }
//}