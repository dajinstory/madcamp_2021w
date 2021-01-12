package com.example.project2_server.di

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


// 서버에서 호출할 메서드를 선언하는 인터페이스
// POST 방식으로 데이터를 주고 받을 때 넘기는 변수는 Field라고 해야한다.
interface RetrofitService {

    @GET("shops/")
    fun getAllShops(
    ) : Call<ArrayList<ShopModel>>

    @GET("liquors/")
    fun getAllLiquors(
    ) : Call<ArrayList<LiquorModel>>

    @GET("myliquors/")
    fun getMyLiquors(
    ) : Call<ArrayList<LiquorModel>>

    @POST("myliquors/")
    fun uploadMyLiquors(
            @Body liquor: LiquorModel
    ) : Call<ResponseBody>

    @Multipart
    @POST("images/upload/")
    fun postImage(
            @Part image: MultipartBody.Part,
            @Part("upload")name: RequestBody
    ): Call<ResponseBody?>?

    //    @FormUrlEncoded
//    @POST("Supplement/List")
//    fun requestList(
//        @Field("s_name") s_name: String
//    ) : Call<ArrayList<SupplementVO>>
//
//    @FormUrlEncoded
//    @POST("Supplement/Single")
//    fun requestSingle(
//        @Field("s_name") s_name: String
//    ) : Call<SupplementVO>

}
