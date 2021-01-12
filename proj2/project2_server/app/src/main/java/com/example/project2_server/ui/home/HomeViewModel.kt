package com.example.project2_server.ui.home

import android.app.Application
import android.content.ContentValues.TAG
import android.database.Cursor
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.AndroidViewModel
import com.example.project2_server.di.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class HomeViewModel(
    application : Application
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    var liquorDB: LiquorDB = LiquorDB.getInstance(context)
    var shopDB: ShopDB = ShopDB.getInstance(context)
    var _liquorCursor: Cursor = liquorDB.sqliteDB.query("LiquorDB", null, null, null, null, null, "name ASC", "6")
    var _shopCursor: Cursor = shopDB.sqliteDB.query("ShopDB", null, null, null, null, null, "name ASC", "6")

    lateinit var retrofit : Retrofit
    lateinit var supplementService : RetrofitService

    fun initRetrofit(){
        retrofit = RetrofitClient.getInstance()
        supplementService = retrofit.create(RetrofitService::class.java)
    }

    fun loadLiquorList(){
        supplementService.getAllLiquors().enqueue(object : Callback<ArrayList<LiquorModel>> {
            override fun onResponse(call: Call<ArrayList<LiquorModel>>, response: Response<ArrayList<LiquorModel>>) {
                if (response.isSuccessful) {
                    liquorDB = LiquorDB.initInstance(context)
                    response.body()!!.forEach { item -> liquorDB.insert(Liquor.fromLiquorModel(item))}
                    _liquorCursor = liquorDB.sqliteDB.query("LiquorDB", null, null, null, null, null, "name ASC", "6")

                } else try {
                    throw Exception("response is not successful")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            override fun onFailure(call: Call<ArrayList<LiquorModel>>, t: Throwable) {
                Log.d(TAG,"실패 : {$t}")
            }
        })
    }
    fun loadShopList(){
        supplementService.getAllShops().enqueue(object : Callback<ArrayList<ShopModel>> {
            override fun onResponse(call: Call<ArrayList<ShopModel>>, response: Response<ArrayList<ShopModel>>) {
                if (response.isSuccessful) {
                    shopDB = ShopDB.initInstance(context)
                    response.body()!!.forEach { item -> shopDB.insert(Shop.fromShopModel(item))}
                    _shopCursor = shopDB.sqliteDB.query("ShopDB", null, null, null, null, null, "name ASC", "6")

                } else try {
                    throw Exception("response is not successful")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            override fun onFailure(call: Call<ArrayList<ShopModel>>, t: Throwable) {
                Log.d(TAG,"실패 : {$t}")
            }
        })
    }
    fun getLiquorCursor(): Cursor{
        return _liquorCursor
    }
    fun getShopCursor(): Cursor{
        return _shopCursor
    }
}