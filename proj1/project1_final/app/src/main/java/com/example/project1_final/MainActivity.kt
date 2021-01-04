package com.example.project1_final

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project1_final.adapter.PageAdapter
import com.example.project1_final.fragments.ContactFragmentTab
import com.example.project1_final.fragments.GalleryFragmentTab
import com.example.project1_final.fragments.TBDFragmentTab
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_tab_button.view.*


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
        tab_layout.setupWithViewPager(viewpager)

        tab_layout.getTabAt(0)?.setCustomView(createView("Contact"))
        tab_layout.getTabAt(1)?.setCustomView(createView("Gallery"))
        tab_layout.getTabAt(2)?.setCustomView(createView("TBD"))

        tab_layout.setSelectedTabIndicatorColor(Color.parseColor("#2222FF"))
        tab_layout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab_layout.getTabAt(0)?.getIcon()?.setColorFilter(resources.getColor(android.R.color.black), PorterDuff.Mode.SRC_IN)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab_layout.getTabAt(2)?.getIcon()?.setColorFilter(resources.getColor(android.R.color.black), PorterDuff.Mode.SRC_IN)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })    }


    private fun createView(tabName: String): View {
        var tabView = LayoutInflater.from(this).inflate(R.layout.item_tab_button, null)
        tabView.tab_text.text = tabName

        when (tabName){
            "Contact" -> {
                tabView.tab_logo.setImageResource(android.R.drawable.ic_menu_call)
                //tabView.tab_logo.setImageResource(R.drawable.phone)
                //tabView.tab_logo.setColorFilter(Color.BLACK)
                return tabView
            }
            "Gallery" -> {
                tabView.tab_logo.setImageResource(android.R.drawable.ic_menu_gallery)
                //tabView.tab_logo.setColorFilter(Color.BLACK)
                return tabView
            }
            "TBD" -> {
                tabView.tab_logo.setImageResource(android.R.drawable.ic_menu_camera)
                //tabView.tab_logo.setColorFilter(Color.BLACK)
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