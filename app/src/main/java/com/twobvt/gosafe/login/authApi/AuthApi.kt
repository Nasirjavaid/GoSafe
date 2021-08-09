package com.twobvt.gosafe.login.authApi

import com.twobvt.gosafe.login.responces.UserLogin
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("/token")
   suspend fun login(

        @Field("username") userName:String,
        @Field("password") password:String,
        @Field("grant_type") grantType:String,

    ) : UserLogin



}