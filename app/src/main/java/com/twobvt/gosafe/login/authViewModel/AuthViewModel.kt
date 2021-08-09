package com.twobvt.gosafe.login.authViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.twobvt.gosafe.login.authRepository.AuthRepository
import com.twobvt.gosafe.login.responces.UserLogin
import com.twobvt.gosafe.network.Resource
import kotlinx.coroutines.launch

 open class AuthViewModel( private val authRepository : AuthRepository) : ViewModel() {

    // getting data from repo
    private val _loginResponse : MutableLiveData<Resource<UserLogin>> = MutableLiveData()
    val loginResponse : LiveData<Resource<UserLogin>> get() = _loginResponse


    fun login(userName:String,
              password:String ,
              grantType:String) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value =authRepository.login(userName,password,grantType)

    }


}