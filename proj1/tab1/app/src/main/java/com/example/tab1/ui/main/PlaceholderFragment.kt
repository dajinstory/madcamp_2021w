package com.example.tab1.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.tab1.R

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // get current tab index
        val tab_index = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1

        // first and second tab
        if (tab_index < 2){
            val root = inflater.inflate(R.layout.fragment_main, container, false)
            val textView: TextView = root.findViewById(R.id.section_label)
            textView.text = "Hello World $tab_index"
//            pageViewModel.tab_index.observe(this, Observer<Int> {
//                textView.text = "Hello World $it"
//            })
            return root
        }
        // third tab
        else {
            val root = inflater.inflate(R.layout.fragment_addressbook, container, false)
            val recyclerView = root.findViewById<RecyclerView>(R.id.contact_info_list)
            recyclerView.adapter = ContactInfoAdapter()
            return root
        }

    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        // X.apply : create X first and then detail X later
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}