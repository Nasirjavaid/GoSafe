package com.twobvt.gosafe.systemIndicatorScreen.indicatorRepository

import com.twobvt.gosafe.baseRepository.BaseRepository
import com.twobvt.gosafe.systemIndicatorScreen.indicatorApi.IndicatorApi


class IndicatorRepository( private  val api : IndicatorApi) : BaseRepository() {


    suspend fun getSystemIndicator() = safeApiCall {

        api.getSystemIndicator()

    }
}