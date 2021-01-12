package com.example.project2_server
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.login.widget.LoginButton

class FBActivity : AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var loginCallback: LoginCallback
    private lateinit var loginButton: LoginButton
    lateinit var accessToken: AccessToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissionList = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (AccessToken.getCurrentAccessToken() != null){
            val mainIntent: Intent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }

        setContentView(R.layout.activity_init)
        callbackManager = CallbackManager.Factory.create()
        loginCallback = LoginCallback()
        loginButton = findViewById(R.id.btn_custom_login)
        loginButton!!.setReadPermissions(listOf("public_profile", "email"))
        loginButton!!.registerCallback(callbackManager, loginCallback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        accessToken = AccessToken.getCurrentAccessToken()
        println(accessToken)
        Log.d("token", accessToken.token)
        println(accessToken.userId)
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}