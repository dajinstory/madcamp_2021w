//package com.example.project2_final.ui.search
//
//import android.app.Application
//import android.database.Cursor
//import androidx.lifecycle.AndroidViewModel
//import com.example.project2_base.di.PhoneBookDatabase
//
//class SearchViewModel(
//    application : Application
//) : AndroidViewModel(application) {
//
//    private val context = getApplication<Application>().applicationContext
//    var phoneBookDatabase: PhoneBookDatabase? = PhoneBookDatabase.getInstance(context)
//    var cursor: Cursor? = phoneBookDatabase?.cursor
//
//    // PhoneBookDatabase 객체와 역할이 일부 겹침.
//}