package com.twobvt.gosafe.geofenceList.uiGeofenceList.geoFenceViewModel


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

open class GeoFenceViewModel( private val geoFenceRepository : GeofenceRepo) : ViewModel() {

    // getting data from repo
//    private val _geofenceResponse : MutableLiveData<Resource<GeoFence>> = MutableLiveData()
//    val geofenceResponse : LiveData<Resource<GeoFence>> get() = _geofenceResponse

    private val _geofenceResponse : MutableLiveData<Resource<GeoFence>> = MutableLiveData()

    val geofenceResponse : LiveData<Resource<GeoFence>> get() = _geofenceResponse




    fun getGeoFenceTreeList() = viewModelScope.launch {
//
//        print(geofenceResponse)


        _geofenceResponse.value =Resource.Loading
        _geofenceResponse.value = geoFenceRepository.geofenceList()
        print("reponse from view model 3")
        print(_geofenceResponse.value)

    }


}