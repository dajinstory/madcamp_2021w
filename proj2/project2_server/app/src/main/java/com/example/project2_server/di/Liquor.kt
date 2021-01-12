package com.example.project2_server.di

import android.database.Cursor
import kotlin.properties.Delegates

class Liquor {
    var _id by Delegates.notNull<Long>()
    lateinit var name: String
    lateinit var type: String
    var price by Delegates.notNull<Long>()
    var degree by Delegates.notNull<Double>()
    lateinit var detail: String
    lateinit var imgUri: String

    companion object {
        fun fromCursor(cursor: Cursor): Liquor {
            val liquor = Liquor()
            liquor._id = cursor.getLong(cursor.getColumnIndex("_id"))
            liquor.name = cursor.getString(cursor.getColumnIndex("name"))
            liquor.type = cursor.getString(cursor.getColumnIndex("type"))
            liquor.price = cursor.getLong(cursor.getColumnIndex("price"))
            liquor.degree = cursor.getDouble(cursor.getColumnIndex("degree"))
            liquor.detail = cursor.getString(cursor.getColumnIndex("detail"))
            liquor.imgUri = cursor.getString(cursor.getColumnIndex("imgUri"))
            return liquor
        }
        fun fromLiquorModel(liquorModel: LiquorModel): Liquor {
            val liquor = Liquor()
            liquor._id = liquorModel._id!!
            liquor.name = liquorModel.name!!
            liquor.type = liquorModel.type!!
            liquor.price = liquorModel.price!!
            liquor.degree = liquorModel.degree!!
            liquor.detail = liquorModel.detail!!
            liquor.imgUri = liquorModel.imgUri!!
            return liquor
        }
    }
}