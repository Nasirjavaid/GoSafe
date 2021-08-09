package com.twobvt.gosafe.vehiclesAndAssets.vaViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu
import kotlinx.coroutines.launch

class VehicleSelectionViewModel : ViewModel() {

    // getting data from repo
    private val _selectedVehiclesDataList : MutableLiveData<List<SubMenu>> = MutableLiveData()
    val selectedVehiclesDataList : LiveData<List<SubMenu>> get() = _selectedVehiclesDataList


    fun addItemToList(subMenu: SubMenu) = viewModelScope.launch {

        _selectedVehiclesDataList.value?.toMutableList()?.add(subMenu)
        println("item Added To list $subMenu")

    }

    fun getItemsItemsFromList(subMenu: SubMenu) = viewModelScope.launch {

        selectedVehiclesDataList.value?.toMutableList()?.add(subMenu)

    }
}