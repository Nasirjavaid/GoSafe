package com.twobvt.gosafe.vehiclesAndAssets.vaRepository

import com.twobvt.gosafe.baseRepository.BaseRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaApi.VaApi


class VaRepository ( private  val api : VaApi) : BaseRepository() {


    suspend fun getVehiclesTreeList() = safeApiCall {


        api.getVehiclesTreeList()
    }
}