package com.example.casinoslots.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casinoslots.model.ResponseWebView
import com.example.casinoslots.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SplashViewModel:ViewModel() {

    private val repository = Repository()
    val webViewUrl: MutableLiveData<Response<ResponseWebView>> = MutableLiveData()

    fun setPostParametersPhone(phoneName:String,locale:String,unique:String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.setParametersPhone(phoneName, locale, unique)
            withContext(Dispatchers.Main){
                webViewUrl.value = response
            }
        }
    }

}