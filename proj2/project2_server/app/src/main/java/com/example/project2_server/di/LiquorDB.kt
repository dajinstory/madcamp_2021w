package com.example.project2_server.di

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class LiquorDB {
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

        sqliteDB.insert("LiquorDB", null, contentValues)
    }

    companion object {
        lateinit var INSTANCE: LiquorDB
        lateinit var dbHelper: LiquorDBHelper

        fun getInstance(context: Context): LiquorDB {
            if (!this::INSTANCE.isInitialized) {
                INSTANCE = LiquorDB()

                // Local DB - SQLiteDB
                dbHelper = LiquorDBHelper(context, "LiquorDB.db", null, 1)
                INSTANCE.sqliteDB = dbHelper.writableDatabase
            }
            return INSTANCE
        }
        fun initInstance(context: Context): LiquorDB {
            dbHelper.onUpgrade(INSTANCE.sqliteDB, 1, 1)
            return INSTANCE
        }
    }
}