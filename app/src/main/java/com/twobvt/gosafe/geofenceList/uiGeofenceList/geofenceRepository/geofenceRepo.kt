package com.twobvt.gosafe.login.authRepository

import com.twobvt.gosafe.baseRepository.BaseRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaApi.geofenceApi


class GeofenceRepo ( private  val api : geofenceApi) : BaseRepository() {




    suspend fun geofenceList() = safeApiCall {


        api.geofenceList()
    }
}