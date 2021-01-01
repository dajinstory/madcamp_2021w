package com.example.proj1_tablayout.model
import android.database.Cursor
import android.provider.MediaStore
import android.provider.ContactsContract
import android.util.Log


class Contact {
    var phoneNumber: String = "NULL"
    var name: String = "NULL"
    var group: String? = "NULL"
    var image: String? = "NULL"

    companion object {
        fun fromCursor(cursor: Cursor?): Contact {
            val contact = Contact()
            contact.phoneNumber = cursor!!.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            contact.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            contact.group = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.IN_VISIBLE_GROUP))
            return contact
        }
    }
}