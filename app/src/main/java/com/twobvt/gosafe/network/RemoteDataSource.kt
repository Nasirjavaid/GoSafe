package com.twobvt.gosafe.network

import com.twobvt.gosafe.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    companion object {
        private const val baseUrl = "http://42.201.208.179:789";
    }


    fun <Api> buildApi(api: Class<Api>, authToken: String? = null): Api {

        // access token identification if available
        println("Access Token Used : $authToken")

        return Retrofit.Builder().baseUrl(baseUrl)
            .client(OkHttpClient().newBuilder()
                .addInterceptor {

                        chain ->
                    chain.proceed(chain.request().newBuilder().also {

                        it.addHeader("Authorization", "Bearer $authToken")

                    }.build())


                }



                .also { client ->

                    if (BuildConfig.DEBUG) {

                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
                        // adding interceptor
                        client.addInterceptor(logging)
                    }
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api);
    }
}