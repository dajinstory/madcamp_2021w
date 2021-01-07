package com.example.project2_base.ui.phone_book.contact

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project2_base.MainActivity
import com.example.project2_base.R
import com.example.project2_base.di.Contact
import com.example.project2_base.di.PhoneBookDatabase
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.item_contact_attribute.view.*
import kotlinx.android.synthetic.main.item_contact_detail.view.*

class ContactFragment : Fragment() {
  lateinit var contactViewModel: ContactViewModel
  lateinit var currentContact: Contact
  lateinit var operation: String
  lateinit var position: String

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
    operation = (activity as ContactActivity).operation
    position = (activity as ContactActivity).position
    return inflater.inflate(R.layout.fragment_contact, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Get Current Contact Info
    when(operation){
      "ADD"->{
        currentContact=contactViewModel.getDefaultContact()
      }
      "EDIT"->{
        currentContact=contactViewModel.getCurrentContact(position.toInt())
        save_button.text = "EDIT"
      }
      else -> {
        Log.e("CurrentContact", "Loading Current Contact Error")
      }
    }

    // fill text
    contact_detail.contact_detail_name.key.text = "name"
    contact_detail.contact_detail_name.value.text = SpannableStringBuilder(currentContact.name)
    contact_detail.contact_detail_phone_number.key.text = "cell #"
    contact_detail.contact_detail_phone_number.value.text = SpannableStringBuilder(currentContact.phoneNumber)
    contact_detail.contact_detail_email.key.text = "email"
    contact_detail.contact_detail_email.value.text = SpannableStringBuilder(currentContact.email)
    contact_detail.contact_detail_group.key.text = "group"
    contact_detail.contact_detail_group.value.text = SpannableStringBuilder(currentContact.group)

    // edit or create contact
    save_button.setOnClickListener {
      // get input data
      currentContact.name = contact_detail.contact_detail_name.value.text.toString()
      currentContact.phoneNumber = contact_detail.contact_detail_phone_number.value.text.toString()
      currentContact.email = contact_detail.contact_detail_email.value.text.toString()
      currentContact.group = contact_detail.contact_detail_group.value.text.toString()

      // update dataset
      when(operation) {
        "ADD" -> {
          PhoneBookDatabase.insertItem(requireContext(), currentContact)
        }
        "EDIT" -> {
          PhoneBookDatabase.editItem(
            requireContext(),
            contactViewModel.cursor,
            position.toInt(),
            currentContact
          )
        }
        else -> {
          Log.e("UpdateError", "Error while update item to database")
        }
      }

      // move to MainActivity
      val returnIntent = Intent(requireContext(), MainActivity::class.java)
      activity?.setResult(Activity.RESULT_OK, returnIntent)
      activity?.finish()
    }
  }
}