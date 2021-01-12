package com.example.sos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.sos.ui.main.SectionsPagerAdapter
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.widget.LoginButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.zxing.datamatrix.decoder.Version
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var loginCallback: LoginCallback
    private lateinit var loginButton: LoginButton
    private lateinit var accessToken: AccessToken

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppEventsLogger.activateApp(application)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        callbackManager = CallbackManager.Factory.create()
        loginCallback = LoginCallback()
        loginButton = findViewById(R.id.btn_custom_login)
        loginButton!!.setReadPermissions(listOf("public_profile", "email"))
        loginButton!!.registerCallback(callbackManager, loginCallback)

        fab.setOnClickListener { view ->
            
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        accessToken = AccessToken.getCurrentAccessToken()
        println(accessToken)
    }


}

