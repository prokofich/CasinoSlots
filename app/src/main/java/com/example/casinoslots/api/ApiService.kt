package com.example.casinoslots.api

import com.example.casinoslots.model.ResponseWebView
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("splash.php")
    suspend fun setPostParametersPhone(
        @Field("phone_name") phoneName:String,
        @Field("locale") locale:String,
        @Field("unique") unique:String
    ): Response<ResponseWebView>

}