package com.twobvt.gosafe.login.responces

data class UserLogin(
    val access_token: String,
    val expires_in: Int,
    val token_type: String
)