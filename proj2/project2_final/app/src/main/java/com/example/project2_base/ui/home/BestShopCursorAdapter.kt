package com.example.project2_final.ui.home

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project2_final.R
import com.example.project2_final.di.Shop
import com.example.project2_final.utils.CursorRecyclerViewAdapter

class BestShopCursorAdapter(private val context : Context, cursor: Cursor)
    : CursorRecyclerViewAdapter<BestShopCursorAdapter.ViewHolder?>(context, cursor) {

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
            //profile = item?.imgUri -> ~~~
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