package com.example.project2_server.di

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class MyLiquorDB {
    lateinit var sqliteDB: SQLiteDatabase

    fun insert(liquor: Liquor){
        val contentValues = ContentValues()
        contentValues.put("_id", liquor._id)
        contentValues.put("name", liquor.name)
        contentValues.put("type", liquor.type)
        contentValues.put("price", liquor.price)
        contentValues.put("degree", liquor.degree)
        contentValues.put("detail", liquor.detail)
        contentValues.put("imgUri", liquor.imgUri)

        sqliteDB.insert("MyLiquorDB", null, contentValues)
    }

    companion object {
        lateinit var INSTANCE: MyLiquorDB
        lateinit var dbHelper: MyLiquorDBHelper

        fun getInstance(context: Context): MyLiquorDB {
            if (!this::INSTANCE.isInitialized) {
                INSTANCE = MyLiquorDB()

                // Local DB - SQLiteDB
                dbHelper = MyLiquorDBHelper(context, "MyLiquorDB.db", null, 1)
                INSTANCE.sqliteDB = dbHelper.writableDatabase
            }
            return INSTANCE
        }
        fun initInstance(context: Context): MyLiquorDB {
            dbHelper.onUpgrade(INSTANCE.sqliteDB, 1, 1)
            return INSTANCE
        }
    }
}