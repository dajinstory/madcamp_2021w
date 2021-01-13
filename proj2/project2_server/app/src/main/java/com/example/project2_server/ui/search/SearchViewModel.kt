package com.example.project2_server.ui.search

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.project2_server.di.*
import com.facebook.FacebookSdk
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class SearchViewModel(
    application : Application
) : AndroidViewModel(application) {

    lateinit var currentPhotoPath: String
    private val context = getApplication<Application>().applicationContext
    var liquorDB: LiquorDB = LiquorDB.getInstance(context)
    var _liquorCursor: Cursor = liquorDB.sqliteDB.query("LiquorDB", null, null, null, null, null, "name ASC")

    lateinit var retrofit : Retrofit
    lateinit var supplementService : RetrofitService

    fun initRetrofit(){
        retrofit = RetrofitClient.getInstance()
        supplementService = retrofit.create(RetrofitService::class.java)
    }

    fun syncLiquorList(){
        supplementService.getAllLiquors().enqueue(object : Callback<ArrayList<LiquorModel>> {
            override fun onResponse(call: Call<ArrayList<LiquorModel>>, response: Response<ArrayList<LiquorModel>>) {
                if (response.isSuccessful) {
                    liquorDB = LiquorDB.initInstance(context)
                    response.body()!!.forEach { item -> liquorDB.insert(Liquor.fromLiquorModel(item))}
                    _liquorCursor = liquorDB.sqliteDB.query("LiquorDB", null, null, null, null, null, "name ASC")

                } else try {
                    throw Exception("response is not successful")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            override fun onFailure(call: Call<ArrayList<LiquorModel>>, t: Throwable) {
                Log.d(ContentValues.TAG,"실패 : {$t}")
            }
        })
    }

    fun createImageFile(context: Context): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        val image: File = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
        currentPhotoPath=image.absolutePath
        return image
    }

    fun getLiquorCursor(): Cursor{
        return _liquorCursor
    }

    fun uploadImage(filePath: String, uri:Uri){
//        val outputStream = ByteArrayOutputStream();
//        val inputStream = context.contentResolver.openInputStream(uri)
//        val bufferSize = 1024
//        val buffer = ByteArray(bufferSize);
//        var len  = 0
//        while (true) {
//            len = inputStream!!.read(buffer)
//            if(len == -1)
//                break;
//            outputStream.write(buffer, 0, bufferSize)
//        }
//
//        val _byte: ByteArray = outputStream.toByteArray()
//        val file = String(_byte)

        val file = File(uri.getPath())
        val reqfile:RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("upload", "test.png", reqfile)
        val name: RequestBody = RequestBody.create(MediaType.parse("image/png"), "upload")

        supplementService.postImage(body, name)!!.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                if (response.code() === 200) {
                    Toast.makeText(FacebookSdk.getApplicationContext(), response.code().toString() + "", Toast.LENGTH_SHORT).show()

                }
                Toast.makeText(FacebookSdk.getApplicationContext(), response.code().toString() + "asdfasdf", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseBody?>?, t: Throwable) {
                Toast.makeText(FacebookSdk.getApplicationContext(), "req fail", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }
}