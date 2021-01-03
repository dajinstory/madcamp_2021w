package com.example.proj1_tablayout.fragments

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proj1_tablayout.ContactActivity
import com.example.proj1_tablayout.R
import com.example.proj1_tablayout.adapter.ContactCursorAdapter
import com.example.proj1_tablayout.model.ContactDatabase
import kotlinx.android.synthetic.main.fragment_contacts.view.*

class ContactFragmentTab : Fragment() {
    var name = ""
    var cursor: Cursor? = null
    var adapter: ContactCursorAdapter? = null
    var recyclerView: RecyclerView? = null

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
        cursor = ContactDatabase.getInstance(requireContext())?.cursor

        // RecyclerView
        recyclerView = view.contacts
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = ContactCursorAdapter(requireContext(), cursor!!)

        // FAB
        view.fab.setOnClickListener{
            val nextIntent = Intent(requireContext(), ContactActivity::class.java)
            val requestCode: Int = 0 // ADD
            nextIntent.putExtra("operation", "add")
            startActivityForResult(nextIntent, requestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        val bundle = data?.extras

        ContactDatabase.updateInstance(requireContext())
        cursor = ContactDatabase.getInstance(requireContext())?.cursor
        recyclerView?.adapter = ContactCursorAdapter(requireContext(), cursor!!)
    }
}