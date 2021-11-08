package com.twobvt.gosafe.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.twobvt.gosafe.baseRepository.BaseRepository
import com.twobvt.gosafe.login.authRepository.AuthRepository
import com.twobvt.gosafe.login.authViewModel.AuthViewModel
import com.twobvt.gosafe.map.mapRepository.MapRepository
import com.twobvt.gosafe.map.mapViewModel.MapViewModel
import com.twobvt.gosafe.systemIndicatorScreen.indicatorRepository.IndicatorRepository
import com.twobvt.gosafe.systemIndicatorScreen.indicatorViewModel.IndicatorViewModel
import com.twobvt.gosafe.vehiclesAndAssets.vaRepository.AssetRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaRepository.VaRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaRepository.VehicleRepository
import com.twobvt.gosafe.vehiclesAndAssets.vaViewModel.AssetViewModel
import com.twobvt.gosafe.vehiclesAndAssets.vaViewModel.VaViewModel
import com.twobvt.gosafe.vehiclesAndAssets.vaViewModel.VehicleViewModel

class ViewModelFactory( private val repository : BaseRepository) : ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(VaViewModel::class.java) ->VaViewModel(repository as VaRepository) as T
            modelClass.isAssignableFrom(VehicleViewModel::class.java) ->VehicleViewModel(repository as VehicleRepository) as T
            modelClass.isAssignableFrom(AssetViewModel::class.java) ->AssetViewModel(repository as AssetRepository) as T
            modelClass.isAssignableFrom(MapViewModel::class.java) ->MapViewModel(repository as MapRepository) as T
            modelClass.isAssignableFrom(IndicatorViewModel::class.java) ->IndicatorViewModel(repository as IndicatorRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass not found")
        }

    }


}