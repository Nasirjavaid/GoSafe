package com.twobvt.gosafe.historyReplay




import com.twobvt.gosafe.baseRepository.BaseRepository


class HistoryReplayRepository(private val api: HistoryReplayApi) : BaseRepository() {




    suspend fun historyReplayList(regNumber:String,historyType:String,startDate:String,endDate:String,speedCheck:Boolean) = safeApiCall {


        api.historyReplayList(regNumber,historyType,startDate,endDate,speedCheck

        )
    }
}