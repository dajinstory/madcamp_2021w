package com.example.proj1_tablayout

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.proj1_tablayout.adapter.PageAdapter
import com.example.proj1_tablayout.fragments.ContactFragmentTab
import com.example.proj1_tablayout.fragments.GalleryFragmentTab
import com.example.proj1_tablayout.fragments.TBDFragmentTab
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_tab_button.view.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contactFragment = ContactFragmentTab()
        contactFragment.name = "Contact"
        val galleryFragment = GalleryFragmentTab()
        galleryFragment.name = "Gallery"
        val tbdFragment = TBDFragmentTab()
        tbdFragment.name = "TBD"

        val adapter = PageAdapter(supportFragmentManager) // PageAdapter 생성
        adapter.addItems(contactFragment)
        adapter.addItems(galleryFragment)
        adapter.addItems(tbdFragment)

        viewpager.adapter = adapter
        tablayout.setupWithViewPager(viewpager)

        tablayout.getTabAt(0)?.setCustomView(createView("Contact"))
        tablayout.getTabAt(1)?.setCustomView(createView("Gallery"))
        tablayout.getTabAt(2)?.setCustomView(createView("TBD"))

    }




    private fun createView(tabName: String): View {
        var tabView = LayoutInflater.from(this).inflate(R.layout.custom_tab_button, null)
        tabView.tab_text.text = tabName

        when (tabName){
            "Contact" -> {
                tabView.tab_logo.setImageResource(android.R.drawable.ic_menu_call)
                return tabView
            }
            "Gallery" -> {
                tabView.tab_logo.setImageResource(android.R.drawable.ic_menu_gallery)
                return tabView
            }
            "TBD" -> {
                tabView.tab_logo.setImageResource(android.R.drawable.ic_menu_camera)
                return tabView
            }
            else -> {
                return tabView
            }
        }

    }
}