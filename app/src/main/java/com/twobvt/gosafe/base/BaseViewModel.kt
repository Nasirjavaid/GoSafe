package com.twobvt.gosafe.base

import androidx.lifecycle.ViewModel
import com.twobvt.gosafe.baseRepository.BaseRepository

 abstract class BaseViewModel(

    private val repository : BaseRepository
) : ViewModel()  {
}