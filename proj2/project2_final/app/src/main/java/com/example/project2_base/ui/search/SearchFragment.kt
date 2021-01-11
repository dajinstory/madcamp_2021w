//package com.example.project2_final.ui.search
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//
//class SearchFragment : Fragment() {
//  var searchText: String = ""
//  var sortOption: String = "ASC"
//  lateinit var homeViewModel: HomeViewModel
//  lateinit var contactRecyclerView: RecyclerView
//
//  override fun onCreateView(
//    inflater: LayoutInflater,
//    container: ViewGroup?,
//    savedInstanceState: Bundle?
//  ): View? {
//
//    homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
//    return inflater.inflate(R.layout.fragment_home,container,false)
//  }
//
//  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//
//    // RecyclerView
//    contactRecyclerView = view.findViewById(R.id.contacts_recycler_view)
//    contactRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//    contactRecyclerView.adapter = SearchCursorAdapter(requireContext(), homeViewModel.cursor!!)
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
//    //////////////////////////////////////////////////////////
//    //  아래 주석 해제시, EDIT한 경우, 동작하지 않은 문제 발생 //
//    //////////////////////////////////////////////////////////
//
////    if (resultCode != Activity.RESULT_OK){
////      return
////    }
////    val bundle = data?.extras
//
//    // update view
//    PhoneBookDatabase.updateInstance(requireContext())
//    homeViewModel.cursor = PhoneBookDatabase.getInstance(requireContext())?.cursor
//    contactRecyclerView.adapter = SearchCursorAdapter(requireContext(), homeViewModel.cursor!!)
//  }
//}