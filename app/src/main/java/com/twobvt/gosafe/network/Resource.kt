package com.twobvt.gosafe.network
import okhttp3.ResponseBody

sealed class Resource<out T> {

    data class Success<out T> (val value:T) : Resource<T>()



    data class Failure(
        val status : Boolean,
        val isNetworkError : Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
        ) : Resource<Nothing>()


    object  Loading : Resource<Nothing>()

    object  ResetScreenState : Resource<Nothing>()

}