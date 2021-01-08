package com.example.project2_base.ui.phonebook

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.project2_base.R
import com.example.project2_base.di.Contact
import com.example.project2_base.di.ContactDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView

class PhonebookActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notes))
        setupActionBarWithNavController(navController, appBarConfiguration)*/
        navView.setupWithNavController(navController)
    }
        // Case #1 : Use Inmemory Dataset with Room
        //var contactsRoomDataset = ContactDatabase.getInstance(this)
        //var contactDataset = contactsRoomDataset?.contactDao()?.getAll() ?: emptyArray<Contact>() as List<Contact>

        // Case #2 : Use Inmemory Object by loading json file
        //var contactDataset = json2contacts("database.json", this)

        // Case #3 : Load by query from phone when necessary
        val cursor = ContactDatabase.getInstance(this)?.cursor
        val operation: String? = intent.getStringExtra("operation")
        val position: Int = intent.getIntExtra("position", -1)

        // Default Info - Add
        var contact: Contact = Contact()
        var name: String = "Name"
        var phoneNumber: String = "Phone Number"
        var email: String = "Email"
        var group: String = "Group"
//        contact.phoneNumber = phoneNumber
//        contact.email = email
//        contact.group = group

//        // Change Info - for Edit
//        if(operation == "edit"){
//            // get contact in DB
//            cursor!!.moveToPosition(position)
//            contact = Contact.fromCursor(cursor)
//
//            // Set variables in xml
//            name = contact.name
//            phoneNumber = contact.phoneNumber
//            email = contact.email
//            group = contact.group
//            save_button.text = "EDIT"
//        }
//
//        // view
//        contact_detail.contact_name.key.text = "name"
//        contact_detail.contact_name.value.text = SpannableStringBuilder(name)
//        contact_detail.contact_phone_number.key.text = "cell #"
//        contact_detail.contact_phone_number.value.text = SpannableStringBuilder(phoneNumber)
//        contact_detail.contact_email.key.text = "email"
//        contact_detail.contact_email.value.text = SpannableStringBuilder(email)
//        contact_detail.contact_group.key.text = "group"
//        contact_detail.contact_group.value.text = SpannableStringBuilder(group)
//
//        // edit or create contact
//        save_button.setOnClickListener {
//            // get input data
//            name = contact_detail.contact_name.value.text.toString()
//            phoneNumber = contact_detail.contact_phone_number.value.text.toString()
//            email = contact_detail.contact_email.value.text.toString()
//            group = contact_detail.contact_group.value.text.toString()
//
//            contact.name = name
//            contact.phoneNumber = phoneNumber
//            contact.email = email
//            contact.group = group
//
//            // update dataset
//            when(operation){
//                "add"->{
//                    ContactDatabase.insertItem(this, contact)
//                }
//                "edit"->{
//                    ContactDatabase.editItem(this, cursor, position, contact)
//                }
//                "access"->{
//                    ContactDatabase.accessItemList(this)
//                }
//
//            }
//            // update database
//            // ContactDatabase.updateInstance(this)
//            // 여기서 업데이트하면 안됨. 비동기냐...?
//
//            // move to MainActivity
//            val returnIntent = Intent(this, MainActivity::class.java)
//            setResult(Activity.RESULT_OK, returnIntent)
//            finish()
//        }
//    }
}