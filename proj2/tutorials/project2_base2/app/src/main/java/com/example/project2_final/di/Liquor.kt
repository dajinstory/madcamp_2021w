package com.example.project2_final.di
import android.database.Cursor
import kotlin.properties.Delegates

class Liquor {
    var _id by Delegates.notNull<Long>()
    lateinit var name: String
    lateinit var type: String
    var price by Delegates.notNull<Float>()
    var degree by Delegates.notNull<Float>()
    lateinit var detail: String
    lateinit var imgUri: String

    companion object {
        fun fromCursor(cursor: Cursor): Liquor {
            val liquor = Liquor()
            liquor._id = cursor.getLong(cursor.getColumnIndex("_id"))
            liquor.name = cursor.getString(cursor.getColumnIndex("name"))
            liquor.type = cursor.getString(cursor.getColumnIndex("type"))
            liquor.price = cursor.getFloat(cursor.getColumnIndex("price"))
            liquor.degree = cursor.getFloat(cursor.getColumnIndex("degree"))
            liquor.detail = cursor.getString(cursor.getColumnIndex("detail"))
            liquor.imgUri = cursor.getString(cursor.getColumnIndex("imgUri"))
            return liquor
        }
    }
}