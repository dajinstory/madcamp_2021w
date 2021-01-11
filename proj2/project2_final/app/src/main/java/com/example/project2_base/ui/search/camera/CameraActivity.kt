//package com.example.project2_base.ui.search.camera
//
//import android.os.Build
//import android.os.Bundle
//import androidx.annotation.RequiresApi
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import com.example.project2_base.R
//
//
//class CameraActivity : AppCompatActivity() {
//    lateinit var contactViewModel: CameraViewModel
//    lateinit var operation: String
//    lateinit var position: String
//
//    @RequiresApi(Build.VERSION_CODES.R)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_contact)
//
//        contactViewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
//        operation = intent.getStringExtra("operation") ?: "ADD"
//        position = intent.getStringExtra("position") ?: "-1"
//
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.contact_fragment, CameraFragment())
//            .commit()
//
//    }
//}
