package com.example.proj1_tablayout.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.proj1_tablayout.R
import com.example.proj1_tablayout.RollingBallGameActivity
import kotlinx.android.synthetic.main.roulettefragment_tab.*


class TBDFragmentTab : Fragment() {
    var name = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.roulettefragment_tab,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageButton = imageButton

        //loading Animation
        val animRotate = AnimationUtils.loadAnimation(
            context,
            R.anim.clockwise_rotate)

        //handling aniamtion on button click
        imageButton.setOnClickListener {
            // starting the animation
            imageButton.startAnimation(animRotate)
        }

        gameStartBtn.setOnClickListener {
            startActivity(Intent(context, RollingBallGameActivity::class.java))
        }

    }



}