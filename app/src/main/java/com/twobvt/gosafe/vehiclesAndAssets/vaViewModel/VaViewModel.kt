package com.twobvt.gosafe.vehiclesAndAssets.vaViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobvt.gosafe.network.Resource
import com.twobvt.gosafe.vehiclesAndAssets.vaRepository.VaRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.VehicleTreeList
import kotlinx.coroutines.launch

open class VaViewModel( private val vaRepository : VaRepository) : ViewModel() {

    // getting data from repo
    private val _vaResponse : MutableLiveData<Resource<VehicleTreeList>> = MutableLiveData()
    val vaResponse : LiveData<Resource<VehicleTreeList>> get() = _vaResponse





    fun getVehiclesTreeList() = viewModelScope.launch {

        _vaResponse.value =Resource.Loading
        _vaResponse.value =vaRepository.getVehiclesTreeList()

    }


}