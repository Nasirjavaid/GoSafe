package com.twobvt.gosafe.vehiclesAndAssets.vaViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobvt.gosafe.vehiclesAndAssets.vaRepository.VehicleRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu
import kotlinx.coroutines.launch

open class VehicleViewModel (private val  vehicleRepository: VehicleRepository) : ViewModel() {

    // getting data from repo
    private val _vehicleList : MutableLiveData<List<SubMenu>> = MutableLiveData()
    val vehicleList : LiveData<List<SubMenu>> get() = _vehicleList


    fun getVehicleList() = viewModelScope.launch {
        _vehicleList.value = VehicleRepository.getVehiclesList()

    }


}