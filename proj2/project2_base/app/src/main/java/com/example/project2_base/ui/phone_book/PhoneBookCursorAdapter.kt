package com.example.project2_base.ui.phone_book

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project2_base.R
import com.example.project2_base.di.Contact
import com.example.project2_base.ui.phone_book.contact.ContactActivity
import com.example.project2_base.utils.CursorRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_contact.view.*


class PhoneBookCursorAdapter(private val context : Context, cursor: Cursor)
    : CursorRecyclerViewAdapter<PhoneBookCursorAdapter.ViewHolder?>(context, cursor) {

    class ViewHolder constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        val _view: View
        val image: ImageView
        val name: TextView
        val phone_number: TextView

        init {
            _view = view
            image = view.findViewById(R.id.contact_profile)
            name = view.findViewById(R.id.contact_name)
            phone_number = view.findViewById(R.id.contact_phone_number)
        }

        fun setItem(item: Contact, position: Int) {
            // image.?? = item?.image ?: "default.png"
            name.text = item?.name ?: "None"
            phone_number.text = item?.phoneNumber ?: "None"
            itemView.setOnClickListener {
                val nextIntent = Intent(_view.context, ContactActivity::class.java)
                val requestCode: Int = 1 // EDIT
                nextIntent.putExtra("operation", "EDIT")
                nextIntent.putExtra("position", position.toString())
                (_view.context as Activity).startActivityForResult(nextIntent, requestCode)
            }
            itemView.contact_call.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:"+item.phoneNumber)
                (_view.context as Activity).startActivityForResult(callIntent, 0)
            }
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