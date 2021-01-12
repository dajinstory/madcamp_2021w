package com.example.project2_server.ui.favorites

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
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class FavoritesViewModel(
    application : Application
) : AndroidViewModel(application) {

    lateinit var currentPhotoPath: String
    private val context = getApplication<Application>().applicationContext
    var myLiquorDB: MyLiquorDB = MyLiquorDB.getInstance(context)
    var myLiquorCursor: Cursor = myLiquorDB.sqliteDB.query("MyLiquorDB", null, null, null, null, null, "name ASC")

    lateinit var retrofit : Retrofit
    lateinit var supplementService : RetrofitService

    fun initRetrofit(){
        retrofit = RetrofitClient.getInstance()
        supplementService = retrofit.create(RetrofitService::class.java)
    }

    fun syncLiquorList(){
        supplementService.getMyLiquors().enqueue(object : Callback<ArrayList<LiquorModel>> {
            override fun onResponse(call: Call<ArrayList<LiquorModel>>, response: Response<ArrayList<LiquorModel>>) {
                if (response.isSuccessful) {
                    myLiquorDB = MyLiquorDB.initInstance(context)
                    response.body()!!.forEach { item -> myLiquorDB.insert(Liquor.fromLiquorModel(item))}
                    myLiquorCursor = myLiquorDB.sqliteDB.query("MyLiquorDB", null, null, null, null, null, "name ASC")

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
        return myLiquorCursor
    }

    fun uploadImage(filePath: String, uri: Uri){
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
        val reqfile: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("upload", filePath.split("/").last(), reqfile)
        val name: RequestBody = RequestBody.create(MediaType.parse("image/png"), "upload")

        supplementService.postImage(body, name)!!.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                if (response.code() === 200) {
                    Toast.makeText(context, response.code().toString() + "SUCCESS", Toast.LENGTH_SHORT).show()

                }
                Toast.makeText(context, response.code().toString() + "OO", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseBody?>?, t: Throwable) {
                Toast.makeText(FacebookSdk.getApplicationContext(), "req fail", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })

        val random = Random()
        val newMyLiquor:LiquorModel = LiquorModel()
        newMyLiquor._id = random.nextLong()
        newMyLiquor.name = filePath.split("/").last()
        newMyLiquor.type = "Mine"
        newMyLiquor.price = 0
        newMyLiquor.degree = 0.0
        newMyLiquor.imgUri = "/static/uploads/" + newMyLiquor.name
        newMyLiquor.detail = "Mine"

        supplementService.uploadMyLiquors(newMyLiquor)!!.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                if (response.code() === 200) {
                    Toast.makeText(context, response.code().toString() + "MyLiquorSUCCESS", Toast.LENGTH_SHORT).show()

                }
                Toast.makeText(context, response.code().toString() + "MyLiquorOO", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseBody?>?, t: Throwable) {
                Toast.makeText(FacebookSdk.getApplicationContext(), "req fail", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })

    }
}