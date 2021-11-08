package com.twobvt.gosafe.historyReplay
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobvt.gosafe.geofenceList.uiGeofenceList.response.Data
import com.twobvt.gosafe.geofenceList.uiGeofenceList.response.GeoFence
import com.twobvt.gosafe.login.authRepository.GeofenceRepo
import com.twobvt.gosafe.network.Resource
import com.twobvt.gosafe.vehiclesAndAssets.vaRepository.VaRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.VaItemList
import kotlinx.coroutines.launch

open class HistoryReplayViewModel( private val historyReplayRepository : HistoryReplayRepository) : ViewModel() {

    // getting data from repo
//    private val _geofenceResponse : MutableLiveData<Resource<GeoFence>> = MutableLiveData()
//    val geofenceResponse : LiveData<Resource<GeoFence>> get() = _geofenceResponse

    private val _historyReplayResponse : MutableLiveData<Resource<HistoryReplay>> = MutableLiveData()

    val historyReplayResponse : LiveData<Resource<HistoryReplay>> get() = _historyReplayResponse




    fun historyReplayList() = viewModelScope.launch {
//
//        print(geofenceResponse)


        _historyReplayResponse.value =Resource.Loading
        _historyReplayResponse.value = historyReplayRepository.historyReplayList(
            "G797W","Replay",
            "2021-07-21 19:00:00.000","2021-07-22 18:59:00.000",false
            )
        print("reponse from view model 6")
        print(_historyReplayResponse.value)

    }


}