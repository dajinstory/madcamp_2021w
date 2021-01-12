package com.example.proj1_tablayout.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proj1_tablayout.ContactActivity
import com.example.proj1_tablayout.R
import com.example.proj1_tablayout.adapter.ContactsAdapter
import com.example.proj1_tablayout.model.ContactDatabase
import com.example.proj1_tablayout.model.Contact
import com.example.proj1_tablayout.model.MediaFileData
import json2contacts
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import java.util.*


class ContactsFragment : Fragment() {
    var name = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_contacts,container,false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Case #1 : Use Inmemory Dataset with Room
        var contactsRoomDataset = ContactDatabase.getInstance(requireContext())
        var contactDataset = contactsRoomDataset?.contactDao()?.getAll() ?: emptyArray<Contact>() as List<Contact>

        // Case #2 : Use Inmemory Object by loading json file
        //var contactDataset = json2contacts("database.json", requireContext())

        // Set RecyclerView
        var recyclerView: RecyclerView = view.contacts
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ContactsAdapter(requireContext(), contactDataset)

        // Set fab
        view.fab.setOnClickListener{
            val nextIntent = Intent(requireContext(), ContactActivity::class.java)
            nextIntent.putExtra("idx", contactDataset.lastIndex)
            startActivityForResult(nextIntent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when (requestCode){
                0 -> {
                    var contactDataset = json2contacts("database.json", requireContext())

                    var recyclerView: RecyclerView = requireView().contacts
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = ContactsAdapter(requireContext(), contactDataset)
                }
            }
        }
    }

    fun loadContactsFromPhone() {
        print(1)
    }
}