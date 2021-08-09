package com.twobvt.gosafe.vehiclesAndAssets.vaApi

import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.VehicleTreeList
import retrofit2.http.GET

interface VaApi {


    @GET("/api/VehicleTree/list/")


    suspend fun getVehiclesTreeList(

        ) : VehicleTreeList

}