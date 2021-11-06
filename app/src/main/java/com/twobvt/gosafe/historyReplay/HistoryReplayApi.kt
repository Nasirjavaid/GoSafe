package com.twobvt.gosafe.historyReplay



import com.twobvt.gosafe.geofenceList.uiGeofenceList.response.GeoFence
import com.twobvt.gosafe.login.responces.UserLogin
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.VaItemList
import retrofit2.http.*

interface HistoryReplayApi {

    @FormUrlEncoded
    @POST("/api/HistoryReplay/view")
    suspend fun historyReplayList(

        @Field("veh_reg_no") regNumber:String,
        @Field("History_type") historyType:String,
        @Field("de_Start") startDate:String,
        @Field("de_End") endDate:String,
        @Field("Speed") speedCheck:Boolean,

    ) : HistoryReplay

}