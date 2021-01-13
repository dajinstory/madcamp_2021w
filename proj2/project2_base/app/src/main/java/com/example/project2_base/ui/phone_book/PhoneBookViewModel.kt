package com.example.project2_base.ui.phone_book

import android.app.Application
import android.content.Intent
import android.database.Cursor
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.AndroidViewModel
import com.example.project2_base.di.PhoneBookDatabase
import com.example.project2_base.ui.phone_book.contact.ContactActivity

class PhoneBookViewModel(
    application : Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    var phoneBookDatabase: PhoneBookDatabase? = PhoneBookDatabase.getInstance(context)
    var cursor: Cursor? = phoneBookDatabase?.cursor

    // PhoneBookDatabase 객체와 역할이 일부 겹침.
}