package com.twobvt.gosafe.vehiclesAndAssets.vaViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobvt.gosafe.vehiclesAndAssets.vaRepository.AssetRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaResponces.SubMenu
import kotlinx.coroutines.launch

open class AssetViewModel ( private val  assetRepository: AssetRepository) : ViewModel() {

    // getting data from repo
    private val _assetList : MutableLiveData<List<SubMenu>> = MutableLiveData()
    val assetList : LiveData<List<SubMenu>> get() = _assetList



    fun getAssetList()  = viewModelScope.launch {

        _assetList.value =AssetRepository.getAssetList()

    }


}