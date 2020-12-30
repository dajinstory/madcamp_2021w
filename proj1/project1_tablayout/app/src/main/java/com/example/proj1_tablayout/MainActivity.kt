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
    companion object {
        const val TAG = "MainActivity"
        const val PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show()
        }
        else{
            requestRuntimePermissions()
        }

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

    private fun requestRuntimePermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Don't need to request
            // It's supported in devices running VERSION_CODES.M or higher
            return
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
            showDialog("Permission granted")
        } else {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isEmpty()) {
                    throw RuntimeException("Empty permission result")
                }
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showDialog("Permission granted")
                } else {
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Log.d(TAG, "User declined, but i can still ask for more")
                        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            PERMISSION_REQUEST_CODE)
                    } else {
                        Log.d(TAG, "User declined and i can't ask")
                        showDialogToGetPermission()
                    }
                }
            }
        }
    }

    private fun showDialog(msg: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Dialog")
            .setMessage(msg)
        val dialog = builder.create()
        dialog.show()
    }

    private fun showDialogToGetPermission() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Permisisons request")
            .setMessage("We need the location permission for some reason. " +
                    "You need to move on Settings to grant some permissions")
        builder.setPositiveButton("OK") { dialogInterface, i ->
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        builder.setNegativeButton("Later") { dialogInterface, i ->
            // ignore
        }
        val dialog = builder.create()
        dialog.show()
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