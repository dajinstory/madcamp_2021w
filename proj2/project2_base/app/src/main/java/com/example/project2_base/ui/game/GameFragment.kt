package com.example.project2_base.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.project2_base.R

class GameFragment : Fragment() {

  private lateinit var gameViewModel: GameViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    gameViewModel =
            ViewModelProvider(this).get(GameViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_game, container, false)
    val textView: TextView = root.findViewById(R.id.text_notifications)
    gameViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })
    return root
  }
}