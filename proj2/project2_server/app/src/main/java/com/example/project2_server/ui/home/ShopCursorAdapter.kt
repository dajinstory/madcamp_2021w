package com.example.project2_server.ui.home

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project2_server.R
import com.example.project2_server.di.Shop
import com.example.project2_server.utils.CursorRecyclerViewAdapter

class ShopCursorAdapter(private val context : Context, cursor: Cursor)
    : CursorRecyclerViewAdapter<ShopCursorAdapter.ViewHolder?>(context, cursor) {

    class ViewHolder constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        val _view: View
        val profile: ImageView
        val name: TextView
        val type: TextView
        val contact: TextView
        val address: TextView
        val businessHour: TextView

        init {
            _view = view
            profile = view.findViewById(R.id.shop_profile)
            name = view.findViewById(R.id.shop_name)
            type = view.findViewById(R.id.shop_type)
            contact = view.findViewById(R.id.shop_contact)
            address = view.findViewById(R.id.shop_address)
            businessHour = view.findViewById(R.id.shop_business_hour)
        }

        fun setItem(item: Shop, position: Int) {
            Glide.with(_view.context).load("http://54.82.239.153:47000"+item.imgUri).into(profile);
            name.text = item.name
            type.text = item.type
            contact.text = item.contact
            address.text = item.address
            businessHour.text = item.businessHour
//            itemView.setOnClickListener {
//                val shopIntent = Intent(_view.context, ShopActivity::class.java)
//                shopIntent.putExtra("position", position)
//                (_view.context as Activity).startActivityForResult(shopIntent, 0)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_shop, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, cursor: Cursor?) {
        val shop: Shop = Shop.fromCursor(cursor!!)
        viewHolder?.setItem(shop, cursor.position)
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }


}