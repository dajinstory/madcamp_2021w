package com.example.proj1_tablayout.fragments

import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_contacts.view.*


class ContactsFragment : Fragment() {
    var name = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_contacts,container,false)

        view.fab.setOnClickListener{
            val nextIntent = Intent(requireContext(), ContactActivity::class.java)
            nextIntent.putExtra("id", -1)
            startActivity(nextIntent)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contactsRoomDataset = ContactDatabase.getInstance(requireContext())
        var contactDataset = contactsRoomDataset?.contactDao()?.getAll() ?: emptyArray<Contact>() as List<Contact>

        val recyclerView: RecyclerView = view.contacts
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ContactsAdapter(requireContext(), contactDataset)
    }

}