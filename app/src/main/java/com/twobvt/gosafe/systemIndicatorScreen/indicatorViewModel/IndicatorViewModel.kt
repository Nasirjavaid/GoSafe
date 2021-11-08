package com.twobvt.gosafe.systemIndicatorScreen.indicatorViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobvt.gosafe.network.Resource
import com.twobvt.gosafe.systemIndicatorScreen.indicatorRepository.IndicatorRepository
import com.twobvt.gosafe.systemIndicatorScreen.indicatorResponses.IndicatorResponses
import kotlinx.coroutines.launch


open class IndicatorViewModel ( private val  indicatorRepository: IndicatorRepository) : ViewModel() {

    // getting data from repo
    private val _indicatorList : MutableLiveData<Resource<IndicatorResponses>> = MutableLiveData()
     val indicatorList : LiveData<Resource<IndicatorResponses>> get() = _indicatorList



    fun getSystemIndicatorList()  = viewModelScope.launch {
        _indicatorList.value = Resource.Loading
        _indicatorList.value = indicatorRepository.getSystemIndicator();

    }


}