package com.example.project2_base.di

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class LeaderBoardDatabase {
    lateinit var cursor: Cursor
    lateinit var sqliteDB: SQLiteDatabase

    fun selectTopN() : Cursor {
        return sqliteDB?.query("LeaderBoard", null, null, null, null, null, "score DESC", "12")
    }

    fun insert(record: Record){
        val contentValues = ContentValues()
        contentValues.put("name", record.name)
        contentValues.put("score", record.score)
        contentValues.put("time", record.time)

        sqliteDB?.insert("LeaderBoard", null, contentValues)
        cursor = selectTopN()
    }

    companion object {
        var INSTANCE: LeaderBoardDatabase? = null

        fun getInstance(context: Context): LeaderBoardDatabase? {
            if (INSTANCE == null) {
                INSTANCE = LeaderBoardDatabase()

                val dbHelper = DBHelper(context, "LeaderBoard.db", null, 1)
                INSTANCE?.sqliteDB = dbHelper.writableDatabase
                INSTANCE?.cursor = INSTANCE?.selectTopN()!!


            }
            return INSTANCE
        }
    }
}