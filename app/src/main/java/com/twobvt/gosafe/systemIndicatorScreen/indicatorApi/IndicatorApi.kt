package com.twobvt.gosafe.systemIndicatorScreen.indicatorApi


import com.twobvt.gosafe.systemIndicatorScreen.indicatorResponses.IndicatorResponses
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface IndicatorApi {

    @FormUrlEncoded
    @POST("/api/Alarm/veh_alrm")
    suspend fun systemIndicatorsList(

        @Field("deviceID") deviceId:String,
        @Field("clusterID") clusterId:String,
        @Field("vehicleID") vehicleId:String,

        ) : IndicatorResponses

}