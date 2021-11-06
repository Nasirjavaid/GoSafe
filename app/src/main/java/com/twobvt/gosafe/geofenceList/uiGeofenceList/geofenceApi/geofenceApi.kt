package com.twobvt.gosafe.vehiclesAndAssets.vaApi

import com.twobvt.gosafe.geofenceList.uiGeofenceList.response.GeoFence
import com.twobvt.gosafe.login.responces.UserLogin
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.VaItemList
import retrofit2.http.*

interface geofenceApi {

//    @FormUrlEncoded
    @GET("/api/GEOFENCES/List")
    suspend fun geofenceList(

    ) : GeoFence

}