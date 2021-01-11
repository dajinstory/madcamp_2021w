//package com.example.project2_base.ui.search.camera
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.text.SpannableStringBuilder
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.project2_base.MainActivity
//import com.example.project2_base.R
//import com.example.project2_base.di.Shop
//import com.example.project2_base.di.PhoneBookDatabase
//import kotlinx.android.synthetic.main.fragment_contact.*
//import kotlinx.android.synthetic.main.item_contact_attribute.view.*
//
//class CameraFragment : Fragment() {
//  lateinit var contactViewModel: CameraViewModel
//  lateinit var currentShop: Shop
//  lateinit var operation: String
//  lateinit var position: String
//
//  override fun onCreateView(
//    inflater: LayoutInflater,
//    container: ViewGroup?,
//    savedInstanceState: Bundle?
//  ): View? {
//
//    contactViewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
//    operation = (activity as CameraActivity).operation
//    position = (activity as CameraActivity).position
//    return inflater.inflate(R.layout.fragment_mypage, container, false)
//  }
//
//  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//
//    // Get Current Contact Info
//    when(operation){
//      "ADD"->{
//        currentShop=contactViewModel.getDefaultContact()
//      }
//      "EDIT"->{
//        currentShop=contactViewModel.getCurrentContact(position.toInt())
//        save_button.text = "EDIT"
//      }
//      else -> {
//        Log.e("CurrentContact", "Loading Current Contact Error")
//      }
//    }
//
//    // fill text
//    contact_detail.contact_detail_name.key.text = "name"
//    contact_detail.contact_detail_name.value.text = SpannableStringBuilder(currentShop.name)
//    contact_detail.contact_detail_phone_number.key.text = "cell #"
//    contact_detail.contact_detail_phone_number.value.text = SpannableStringBuilder(currentShop.phoneNumber)
//    contact_detail.contact_detail_email.key.text = "email"
//    contact_detail.contact_detail_email.value.text = SpannableStringBuilder(currentShop.email)
//    contact_detail.contact_detail_group.key.text = "group"
//    contact_detail.contact_detail_group.value.text = SpannableStringBuilder(currentShop.group)
//
//    // edit or create contact
//    save_button.setOnClickListener {
//      // get input data
//      currentShop.name = contact_detail.contact_detail_name.value.text.toString()
//      currentShop.phoneNumber = contact_detail.contact_detail_phone_number.value.text.toString()
//      currentShop.email = contact_detail.contact_detail_email.value.text.toString()
//      currentShop.group = contact_detail.contact_detail_group.value.text.toString()
//
//      // update dataset
//      when(operation) {
//        "ADD" -> {
//          PhoneBookDatabase.insertItem(requireContext(), currentShop)
//        }
//        "EDIT" -> {
//          PhoneBookDatabase.editItem(
//            requireContext(),
//            contactViewModel.cursor,
//            position.toInt(),
//            currentShop
//          )
//        }
//        else -> {
//          Log.e("UpdateError", "Error while update item to database")
//        }
//      }
//
//      // move to MainActivity
//      val returnIntent = Intent(requireContext(), MainActivity::class.java)
//      activity?.setResult(Activity.RESULT_OK, returnIntent)
//      activity?.finish()
//    }
//  }
//}