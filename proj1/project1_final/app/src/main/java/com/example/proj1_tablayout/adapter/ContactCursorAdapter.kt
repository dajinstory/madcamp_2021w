package com.example.proj1_tablayout.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.example.proj1_tablayout.ContactActivity
import com.example.proj1_tablayout.MainActivity
import com.example.proj1_tablayout.R
import com.example.proj1_tablayout.model.Contact


class ContactCursorAdapter(private val context : Context, cursor: Cursor)
    :CursorRecyclerViewAdapter<ContactCursorAdapter.ViewHolder?>(context, cursor) {

    class ViewHolder constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        val _view: View
        val image: ImageView
        val name: TextView
        val phone_number: TextView

        fun setItem(item: Contact, position: Int) {
            // image.?? = item?.image ?: "default.png"
            name.text = item?.name ?: "None"
            phone_number.text = item?.phoneNumber ?: "None"
            itemView.setOnClickListener {
                val nextIntent = Intent(_view.context, ContactActivity::class.java)
                val requestCode: Int = 1 // EDIT
                nextIntent.putExtra("operation", "edit")
                nextIntent.putExtra("position", position)
                (_view.context as Activity).startActivityForResult(nextIntent, requestCode)
            }
        }

        init {
            _view = view
            image = view.findViewById(R.id.contact_image)
            name = view.findViewById(R.id.contact_name)
            phone_number = view.findViewById(R.id.contact_phone_number)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, cursor: Cursor?) {
        val contact: Contact = Contact.fromCursor(cursor)
        viewHolder!!.setItem(contact, cursor!!.position)
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }


}