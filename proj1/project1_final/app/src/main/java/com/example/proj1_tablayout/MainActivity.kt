package com.example.proj1_tablayout

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proj1_tablayout.adapter.PageAdapter
import com.example.proj1_tablayout.fragments.ContactFragmentTab
import com.example.proj1_tablayout.fragments.GalleryFragmentTab
import com.example.proj1_tablayout.fragments.TBDFragmentTab
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_tab_button.view.*


class MainActivity : AppCompatActivity() {

    var adapter: PageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contactFragment = ContactFragmentTab()
        contactFragment.name = "Contact"
        val galleryFragment = GalleryFragmentTab()
        galleryFragment.name = "Gallery"
        val tbdFragment = TBDFragmentTab()
        tbdFragment.name = "TBD"

        adapter = PageAdapter(supportFragmentManager) // PageAdapter 생성
        adapter?.addItems(contactFragment)
        adapter?.addItems(galleryFragment)
        adapter?.addItems(tbdFragment)

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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            0 -> {
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "연락처 추가", Toast.LENGTH_LONG).show();
                    adapter?.getItem(0)?.onActivityResult(requestCode, resultCode, data)
                }
            }
            1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "연락처 수정", Toast.LENGTH_LONG).show();
                    adapter?.getItem(0)?.onActivityResult(requestCode, resultCode, data)
                }
            }
        }
    }
}