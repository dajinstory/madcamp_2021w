package com.example.project2_base.ui.func

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.project2_base.R

class FuncFragment : Fragment() {

    private lateinit var funcViewModel: FuncViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        funcViewModel =
                ViewModelProvider(this).get(FuncViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_func, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        funcViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}