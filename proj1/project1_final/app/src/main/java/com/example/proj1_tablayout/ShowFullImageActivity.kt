package com.example.proj1_tablayout

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.show_full_image_activity.*

class ShowFullImageActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide();
        supportActionBar?.hide();

        setContentView(R.layout.show_full_image_activity)

        val uri = Uri.parse(intent.getStringExtra("uri"))
        photoview.setImageURI(uri)
    }
}