package com.example.proj1_tablayout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import com.example.proj1_tablayout.model.Contact
import com.example.proj1_tablayout.model.ContactDatabase
import json2contacts
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.item_contact_column.view.*
import kotlinx.android.synthetic.main.item_contact_detail.view.*
import saveContactsToJson

class ContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        // Case #1 : Use Inmemory Dataset with Room
        var contactsRoomDataset = ContactDatabase.getInstance(this)
        var contactDataset = contactsRoomDataset?.contactDao()?.getAll() ?: emptyArray<Contact>() as List<Contact>

        // Case #2 : Use Inmemory Object by loading json file
        //var contactDataset = json2contacts("database.json", this)

        // Get Id of contact
        var idx: Int = intent.getIntExtra("idx", -1)
        var id:Int = contactDataset[idx]?.id ?: contactDataset[idx-1]?.id+1 ?: 1
        var name: String = contactDataset[idx]?.name ?: "name"
        var phoneNumber: String = contactDataset[idx]?.phoneNumber ?: "phone_number"
        var email: String = contactDataset[idx]?.email ?: "email"
        var group: String = contactDataset[idx]?.group ?: "group"

        contact_detail.key_name.row_keyword.text = "name"
        contact_detail.key_phone_number.row_keyword.text = "cell #"
        contact_detail.key_email.row_keyword.text = "e-mail"
        contact_detail.key_group.row_keyword.text = "group"
        contact_detail.key_name.row_value.text = SpannableStringBuilder(name)
        contact_detail.key_phone_number.row_value.text = SpannableStringBuilder(phoneNumber)
        contact_detail.key_email.row_value.text = SpannableStringBuilder(email)
        contact_detail.key_group.row_value.text = SpannableStringBuilder(group)

        // edit or create contact
        save_button.setOnClickListener {
            // get input data
            name = contact_detail.key_name.row_value.text.toString()
            phoneNumber = contact_detail.key_phone_number.row_value.text.toString()
            email = contact_detail.key_email.row_value.text.toString()
            group = contact_detail.key_group.row_value.text.toString()
            val new_contact = Contact(id, name, phoneNumber, email, group, "None")

            // update dataset
            if (idx == contactDataset.lastIndex){
                contactsRoomDataset!!.contactDao().insert(new_contact)

            } else{
                contactsRoomDataset!!.contactDao().deleteByUserId(id)
                contactsRoomDataset!!.contactDao().insert(new_contact)
            }

            // move to mainactivity
            val returnIntent = Intent(this, MainActivity::class.java)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}