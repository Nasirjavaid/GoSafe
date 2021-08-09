package com.twobvt.gosafe.login.authRepository

import com.twobvt.gosafe.baseRepository.BaseRepository
import com.twobvt.gosafe.login.authApi.AuthApi

class AuthRepository ( private  val api : AuthApi ) : BaseRepository() {


    suspend fun login(userName:String , password:String, grantType:String) = safeApiCall {


        api.login(userName,password,grantType)
    }
}