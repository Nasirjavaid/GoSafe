package com.twobvt.gosafe.geofenceMapScreen




import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobvt.gosafe.geofenceList.uiGeofenceList.response.Data
import com.twobvt.packetparcer.PacketParser
import com.twobvt.packetparcer.ParcerModel
import kotlinx.coroutines.launch



open class GeofenceMapViewModel (private val  geoFenceMapRepository: GeoFenceMapRepository ) : ViewModel() {

    // getting data from repo
    private val _singleItem : MutableLiveData<Data> = MutableLiveData()
    val singleItem : LiveData<Data> get() = _singleItem


    private val _packetParser : MutableLiveData<ParcerModel> = MutableLiveData()
    val packetParser : LiveData<ParcerModel> get() = _packetParser


    fun getVehicle() = viewModelScope.launch {

        _singleItem.value = GeoFenceMapRepository.getVehicle()

        // println("Vehicle in view model : ${ _vehicle.value}")


    }


    fun getPacketParser() = viewModelScope.launch {
        _packetParser.value = PacketParser.packetParserFun(singleItem.value?.FenceParam.toString())

         println("Packet parcer in view model : ${ _packetParser.value}")

    }



}