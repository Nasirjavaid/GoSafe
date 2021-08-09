package com.twobvt.gosafe.baseRepository

import com.twobvt.gosafe.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

 abstract class BaseRepository {

    suspend fun <T> safeApiCall(

        apiCall: suspend ()-> T
    ) : Resource<T>{

        return withContext(Dispatchers.IO){

            try {

               Resource.Success( apiCall.invoke())
            } catch (throwable: Throwable){

                when(throwable){

                    is HttpException -> {
                        Resource.Failure(false,false,throwable.code(),throwable.response()?.errorBody())
                    }
                    else -> {
                        Resource.Failure(false,true,null,null)
                    }
                }
            }
        }
    }
}