package com.twobvt.gosafe.map.mapViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobvt.gosafe.map.mapRepository.MapRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu
import com.twobvt.packetparcer.PacketParser
import com.twobvt.packetparcer.ParcerModel
import kotlinx.coroutines.launch



open class MapViewModel (private val  mapRepository: MapRepository ) : ViewModel() {

    // getting data from repo
    private val _vehicle : MutableLiveData<SubMenu> = MutableLiveData()
    val vehicle : LiveData<SubMenu> get() = _vehicle


    private val _packetParser : MutableLiveData<ParcerModel> = MutableLiveData()
    val packetParser : LiveData<ParcerModel> get() = _packetParser


    fun getVehicle() = viewModelScope.launch {

        _vehicle.value = MapRepository.getVehicle()

       // println("Vehicle in view model : ${ _vehicle.value}")


    }


    fun getPacketParser() = viewModelScope.launch {
        _packetParser.value = PacketParser.packetParserFun(vehicle.value?.datatrack.toString())

       // println("Packet parcer in view model : ${ _packetParser.value}")

    }



}