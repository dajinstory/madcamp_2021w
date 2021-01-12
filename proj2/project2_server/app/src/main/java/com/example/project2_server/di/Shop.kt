package com.example.project2_server.di

import android.database.Cursor
import kotlin.properties.Delegates

class Shop {
    var _id by Delegates.notNull<Long>()
    lateinit var name: String
    lateinit var type: String
    lateinit var contact: String
    lateinit var address: String
    lateinit var businessHour: String
    lateinit var detail: String
    lateinit var imgUri: String

    companion object {
        fun fromCursor(cursor: Cursor): Shop {
            val shop = Shop()
            shop._id = cursor.getLong(cursor.getColumnIndex("_id"))
            shop.name = cursor.getString(cursor.getColumnIndex("name"))
            shop.type = cursor.getString(cursor.getColumnIndex("type"))
            shop.contact = cursor.getString(cursor.getColumnIndex("contact"))
            shop.address = cursor.getString(cursor.getColumnIndex("address"))
            shop.businessHour = cursor.getString(cursor.getColumnIndex("businessHour"))
            shop.detail = cursor.getString(cursor.getColumnIndex("detail"))
            shop.imgUri = cursor.getString(cursor.getColumnIndex("imgUri"))
            return shop
        }

        fun fromShopModel(shopModel: ShopModel): Shop {
            val shop = Shop()
            shop._id = shopModel._id!!
            shop.name = shopModel.name!!
            shop.type = shopModel.type!!
            shop.contact = shopModel.contact!!
            shop.address = shopModel.address!!
            shop.businessHour = shopModel.businessHour!!
            shop.detail = shopModel.detail!!
            shop.imgUri = shopModel.imgUri!!
            return shop
        }
    }
}