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
import com.example.project2_final.di.Liquor
import com.example.project2_final.utils.CursorRecyclerViewAdapter

class BestLiquorCursorAdapter(private val context : Context, cursor: Cursor)
    : CursorRecyclerViewAdapter<BestLiquorCursorAdapter.ViewHolder?>(context, cursor) {

    class ViewHolder constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        val _view: View
        val profile: ImageView
        val name: TextView
        val degree: TextView

        init {
            _view = view
            profile = view.findViewById(R.id.liquor_profile)
            name = view.findViewById(R.id.liquor_name)
            degree = view.findViewById(R.id.liquor_degree)
        }

        fun setItem(item: Liquor, position: Int) {
            //profile = item?.imgUri -> ~~~
            name.text = item.name
            degree.text = item.degree.toString()
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
        val liquor: Liquor = Liquor.fromCursor(cursor!!)
        viewHolder?.setItem(liquor, cursor.position)
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }


}