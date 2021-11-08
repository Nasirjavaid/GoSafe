package com.twobvt.gosafe.systemIndicatorScreen.indicatorApi

import com.twobvt.gosafe.systemIndicatorScreen.indicatorResponses.IndicatorResponses
import retrofit2.http.GET

interface IndicatorApi {

    @GET("/api/VehicleTree/list/")

    suspend fun getSystemIndicator(

    ) : IndicatorResponses

}