package com.example.project2_base.ui.phone_book

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project2_base.R
import com.example.project2_base.di.PhoneBookDatabase
import com.example.project2_base.ui.phone_book.contact.ContactActivity
import kotlinx.android.synthetic.main.fragment_phone_book.*

class PhoneBookFragment : Fragment() {
  var searchText: String = ""
  var sortOption: String = "ASC"
  lateinit var phoneBookViewModel: PhoneBookViewModel
  lateinit var contactRecyclerView: RecyclerView

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    phoneBookViewModel = ViewModelProvider(this).get(PhoneBookViewModel::class.java)
    return inflater.inflate(R.layout.fragment_phone_book,container,false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // RecyclerView
    contactRecyclerView = view.findViewById(R.id.contacts_recycler_view)
    contactRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    contactRecyclerView.adapter = PhoneBookCursorAdapter(requireContext(), phoneBookViewModel.cursor!!)

    // FAB
    add_fab.setOnClickListener{
      //Toast.makeText(requireContext(), RecordDatabase.getInstance(requireContext())?.cursor?.count.toString(), Toast.LENGTH_LONG).show()
      val nextIntent = Intent(requireContext(), ContactActivity::class.java)
      val requestCode: Int = 10000 // ADD
      nextIntent.putExtra("operation", "ADD")
      nextIntent.putExtra("position", "-1")
      startActivityForResult(nextIntent, requestCode)
    }
  }

  override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    //////////////////////////////////////////////////////////
    //  아래 주석 해제시, EDIT한 경우, 동작하지 않은 문제 발생 //
    //////////////////////////////////////////////////////////

//    if (resultCode != Activity.RESULT_OK){
//      return
//    }
//    val bundle = data?.extras

    // update view
    PhoneBookDatabase.updateInstance(requireContext())
    phoneBookViewModel.cursor = PhoneBookDatabase.getInstance(requireContext())?.cursor
    contactRecyclerView.adapter = PhoneBookCursorAdapter(requireContext(), phoneBookViewModel.cursor!!)
  }
}