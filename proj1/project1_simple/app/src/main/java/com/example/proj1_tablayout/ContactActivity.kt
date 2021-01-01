package com.example.proj1_tablayout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import com.example.proj1_tablayout.model.Contact
import com.example.proj1_tablayout.model.ContactDatabase
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.item_contact_column.view.*
import kotlinx.android.synthetic.main.item_contact_detail.view.*

class ContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        var contactsRoomDataset = ContactDatabase.getInstance(this)
        var contactDataset = contactsRoomDataset?.contactDao()?.getAll() ?: emptyArray<Contact>() as List<Contact>

        var id: Int = intent.getIntExtra("id", -1)

        contact_detail.key_name.row_keyword.text = "name"
        contact_detail.key_phone_number.row_keyword.text = "cell #"
        contact_detail.key_email.row_keyword.text = "e-mail"
        contact_detail.key_group.row_keyword.text = "group"

        if(id!=1){
            contact_detail.key_name.row_value.text = SpannableStringBuilder(contactDataset[id].name)
            contact_detail.key_phone_number.row_value.text = SpannableStringBuilder(contactDataset[id].phoneNumber)
            contact_detail.key_email.row_value.text = SpannableStringBuilder(contactDataset[id].email)
            contact_detail.key_group.row_value.text = SpannableStringBuilder(contactDataset[id].group)
        }

        save_button.setOnClickListener {
            // delete
            if(id!=-1){
                contactsRoomDataset!!.contactDao().deleteByUserId(id)
            }

            // add
            val newId = contactDataset[contactDataset.lastIndex].id + 1
            val name = contact_detail.key_name.row_value.text.toString()
            val phoneNumber = contact_detail.key_phone_number.row_value.text.toString()
            val email = contact_detail.key_email.row_value.text.toString()
            val group = contact_detail.key_group.row_value.text.toString()
            val new_contact = Contact(newId, name, phoneNumber, email, group, "None")
            contactsRoomDataset!!.contactDao().insert(new_contact)

            // update database
            contactsRoomDataset = ContactDatabase.getInstance(this)
            contactDataset = contactsRoomDataset?.contactDao()?.getAll() ?: emptyArray<Contact>() as List<Contact>

            // move to mainactivity
            val returnIntent = Intent(this, MainActivity::class.java)
//            returnIntent.putExtra("del_id", id)
//            returnIntent.putExtra("new_id", newId)
//            returnIntent.putExtra("new_name", name)
//            returnIntent.putExtra("new_phone_number", phoneNumber)
//            returnIntent.putExtra("new_email", email)
//            returnIntent.putExtra("new_group", group)

            setResult(RESULT_OK, returnIntent)
        }
    }
}