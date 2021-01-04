package com.example.project1_final.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.project1_final.fragments.GalleryFragmentTab

class PageAdapter(fm:FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var fragments : ArrayList<Fragment> = ArrayList()

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    fun addItems(fragment : Fragment){
        fragments.add(fragment)
    }
}