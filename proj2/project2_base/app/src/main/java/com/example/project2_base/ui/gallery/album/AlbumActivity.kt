package com.example.project2_base.ui.gallery.album

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.project2_base.R


class AlbumActivity : AppCompatActivity() {
    lateinit var contactViewModel: AlbumViewModel
    lateinit var albumName: String

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        contactViewModel = ViewModelProvider(this).get(AlbumViewModel::class.java)
        albumName = intent.getStringExtra("albumName") ?: "NONE"

        supportFragmentManager.beginTransaction()
            .replace(R.id.album_fragment, AlbumFragment())
            .commit()

    }
}
