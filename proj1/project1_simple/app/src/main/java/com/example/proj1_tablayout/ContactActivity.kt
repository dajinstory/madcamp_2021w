package com.example.proj1_tablayout

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.proj1_tablayout.model.Contact
import com.example.proj1_tablayout.model.ContactDatabase
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.item_contact_column.view.*
import kotlinx.android.synthetic.main.item_contact_detail.view.*

class ContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val contactsRoomDataset = ContactDatabase.getInstance(this)
        var contactDataset = contactsRoomDataset?.contactDao()?.getAll() ?: emptyArray<Contact>() as List<Contact>

        val id: Int = intent.getIntExtra("id", -1)

        contact_detail.key_name.row_keyword.text = "name"
        contact_detail.key_phone_number.row_keyword.text = "cell #"
        contact_detail.key_email.row_keyword.text = "e-mail"
        contact_detail.key_group.row_keyword.text = "group"
        contact_detail.key_name.row_value.text = SpannableStringBuilder(contactDataset[id].name)
        contact_detail.key_phone_number.row_value.text = SpannableStringBuilder(contactDataset[id].phoneNumber)
        contact_detail.key_email.row_value.text = SpannableStringBuilder(contactDataset[id].email)
        contact_detail.key_group.row_value.text = SpannableStringBuilder(contactDataset[id].group)

        save_button.setOnClickListener {
            print(contact_detail.key_name.row_value.text.toString())
//            val nextIntent = Intent(this, MainActivity::class.java)
//            nextIntent.putExtra("id", id)
//            startActivity(nextIntent)
        }
    }
}