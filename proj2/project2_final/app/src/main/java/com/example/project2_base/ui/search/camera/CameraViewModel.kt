//package com.example.project2_base.ui.search.camera
//
//import android.app.Application
//import android.database.Cursor
//import androidx.lifecycle.AndroidViewModel
//import com.example.project2_base.di.Shop
//import com.example.project2_base.di.PhoneBookDatabase
//
//class CameraViewModel(
//    application : Application
//) : AndroidViewModel(application) {
//
//    private val context = getApplication<Application>().applicationContext
//    var phoneBookDatabase: PhoneBookDatabase? = PhoneBookDatabase.getInstance(context)
//    var cursor: Cursor? = phoneBookDatabase?.cursor
//
//    fun getDefaultContact():Shop{
//        var shop: Shop = Shop()
//        var name: String = "이름"
//        var phoneNumber: String = "전화번호"
//        var email: String = "이메일"
//        var group: String = "그룹"
//        return shop
//    }
//    fun getCurrentContact(position:Int):Shop{
//        cursor!!.moveToPosition(position)
//        return Shop.fromCursor(cursor)
//    }
//
//}