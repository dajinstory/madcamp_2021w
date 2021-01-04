package com.example.project1_final.model
import android.database.Cursor

class Record {
    var name: String = "NULL"
    var score: String = "NULL"
    var time: String = "NULL"

    companion object {
        fun fromCursor(cursor: Cursor?): Record {
            val score = Record()
            score.name = cursor!!.getString(cursor.getColumnIndex("name"))
            score.score = cursor!!.getString(cursor.getColumnIndex("score"))
            score.time = cursor!!.getString(cursor.getColumnIndex("time"))
            return score
        }
    }
}