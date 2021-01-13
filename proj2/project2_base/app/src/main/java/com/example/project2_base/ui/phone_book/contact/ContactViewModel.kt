package com.example.project2_base.ui.phone_book.contact

import android.app.Application
import android.database.Cursor
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.example.project2_base.di.Contact
import com.example.project2_base.di.PhoneBookDatabase
import kotlinx.android.synthetic.main.item_contact_detail.view.*

class ContactViewModel(
    application : Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    var phoneBookDatabase: PhoneBookDatabase? = PhoneBookDatabase.getInstance(context)
    var cursor: Cursor? = phoneBookDatabase?.cursor

    fun getDefaultContact():Contact{
        var contact: Contact = Contact()
        var name: String = "이름"
        var phoneNumber: String = "전화번호"
        var email: String = "이메일"
        var group: String = "그룹"
        return contact
    }
    fun getCurrentContact(position:Int):Contact{
        cursor!!.moveToPosition(position)
        return Contact.fromCursor(cursor)
    }

}