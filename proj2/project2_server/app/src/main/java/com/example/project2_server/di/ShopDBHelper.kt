package com.example.project2_server.di

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ShopDBHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        var sql : String = "CREATE TABLE if not exists ShopDB (" +
                "_id integer primary key autoincrement," +
                "name text,"+
                "type text,"+
                "contact text,"+
                "address text,"+
                "businessHour text,"+
                "detail text,"+
                "imgUri text);";

        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql : String = "DROP TABLE if exists ShopDB"

        db.execSQL(sql)
        onCreate(db)
    }

}