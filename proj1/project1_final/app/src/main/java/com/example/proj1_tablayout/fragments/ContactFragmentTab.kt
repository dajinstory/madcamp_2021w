package com.example.proj1_tablayout.fragments

import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proj1_tablayout.R
import com.example.proj1_tablayout.adapter.ContactCursorAdapter
import kotlinx.android.synthetic.main.fragment_contacts.view.*

class ContactFragmentTab : Fragment() {
    var name = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts,container,false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cursor
        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.IN_VISIBLE_GROUP,
        )
        val selectionClause = null
        val selectionArgs = emptyArray<String>()
        val sortOrder = "${ContactsContract.Contacts.DISPLAY_NAME} DESC"
        val cursor = context?.contentResolver?.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            selectionClause,
            selectionArgs,
            sortOrder
        )

        // RecyclerView
        val recyclerView: RecyclerView = view.contacts
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ContactCursorAdapter(requireContext(), cursor!!)
    }
}