package com.example.proj1_tablayout.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proj1_tablayout.R
import kotlinx.android.synthetic.main.fragment_tab.view.*


class TBDFragmentTab : Fragment() {
    var name = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_tab,container,false)
        view.textView.text = name
        return view
    }



}