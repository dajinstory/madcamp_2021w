package com.example.project2_final.ui.home

import android.app.Application
import android.content.Context
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import com.example.project2_final.di.LiquorDB
import com.example.project2_final.di.ShopDB

class HomeViewModel(
    application : Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    var liquorDB: LiquorDB = LiquorDB.getInstance(context)
    var shopDB: ShopDB = ShopDB.getInstance(context)

    fun loadFromServer(context: Context){
        
    }
    fun getBestLiquorCursor(): Cursor{
        return liquorDB.sqliteDB.query("LiquorDB", null, null, null, null, null, "name ASC", "6")
    }
    fun getBestShopCursor(): Cursor{
        return shopDB.sqliteDB.query("ShopDB", null, null, null, null, null, "name ASC", "6")
    }
}