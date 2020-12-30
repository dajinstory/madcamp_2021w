//package com.example.tab1.ui.main
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.kotlintutorial.ContactInfo
//import com.example.tab1.R
//
//
//class ContactInfoAdapter() : RecyclerView.Adapter<ContactInfoAdapter.ContactInfoViewHolder>(){
//
//    var contactInfoDataset = arrayListOf<ContactInfo>(
//        ContactInfo("1", "010-xxxx-xxxx", "None"),
//        ContactInfo("2", "010-xxxx-xxxx", "None"),
//        ContactInfo("3", "010-xxxx-xxxx", "None"),
//        ContactInfo("4", "010-xxxx-xxxx", "None"),
//        ContactInfo("5", "010-xxxx-xxxx", "None"),
//        ContactInfo("6", "010-xxxx-xxxx", "None"),
//        ContactInfo("7", "010-xxxx-xxxx", "None"),
//        ContactInfo("8", "010-xxxx-xxxx", "None"),
//        ContactInfo("9", "010-xxxx-xxxx", "None")
//    )
//
//    class ContactInfoViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactInfoAdapter.ContactInfoViewHolder {
//        val unit_person_view = LayoutInflater.from(parent.context).inflate(R.layout.unit_person, parent, false)
//        val textView = unit_person_view.findViewById<TextView>(R.id.person_name)
//        return ContactInfoViewHolder(textView)
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    override fun onBindViewHolder(holder: ContactInfoViewHolder, position: Int) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        //holder.bind(personInfoDataset.get(position))
//        holder.textView.text = contactInfoDataset[position].name
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    override fun getItemCount() = contactInfoDataset.size
//}

package com.example.tab1.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintutorial.ContactInfo
import com.example.tab1.R


class ContactInfoAdapter()
    : RecyclerView.Adapter<ContactInfoViewHolder>() {

    var contactInfoDataset = arrayListOf<ContactInfo>(
        ContactInfo("1", "010-xxxx-xxxx", "None"),
        ContactInfo("2", "010-xxxx-xxxx", "None"),
        ContactInfo("3", "010-xxxx-xxxx", "None"),
        ContactInfo("4", "010-xxxx-xxxx", "None"),
        ContactInfo("5", "010-xxxx-xxxx", "None"),
        ContactInfo("6", "010-xxxx-xxxx", "None"),
        ContactInfo("7", "010-xxxx-xxxx", "None"),
        ContactInfo("8", "010-xxxx-xxxx", "None"),
        ContactInfo("9", "010-xxxx-xxxx", "None")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ContactInfoViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ContactInfoViewHolder, position: Int) {
        val contactInfo: ContactInfo = contactInfoDataset[position]
        holder.bind(contactInfo)
    }

    override fun getItemCount(): Int = contactInfoDataset.size

}

class ContactInfoViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.unit_person, parent, false)) {
    private var pNameView: TextView? = null
    //private var mPhoneNumberView: TextView? = null

    init {
        pNameView = itemView.findViewById(R.id.person_name)
    }

    fun bind(contactInfo: ContactInfo) {
        pNameView?.text = contactInfo.name
    }

}