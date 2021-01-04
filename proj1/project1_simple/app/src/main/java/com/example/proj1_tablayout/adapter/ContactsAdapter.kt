package com.example.proj1_tablayout.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proj1_tablayout.ContactActivity
import com.example.proj1_tablayout.R
import com.example.proj1_tablayout.model.Contact


class ContactsAdapter(private val context : Context, private val dataset: List<Contact>)
    : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    class ContactViewHolder(private val view: View):
        RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.contact_image)
        val name: TextView = view.findViewById(R.id.contact_name)
        val phone_number: TextView = view.findViewById(R.id.contact_phone_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = dataset.getOrNull(position)

        holder.name.text = item?.name ?: "None"
        holder.phone_number.text = item?.phoneNumber ?: "None"
        holder.itemView.setOnClickListener{
            val nextIntent = Intent(context, ContactActivity::class.java)
            nextIntent.putExtra("idx", position)
            context.startActivity(nextIntent)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}
