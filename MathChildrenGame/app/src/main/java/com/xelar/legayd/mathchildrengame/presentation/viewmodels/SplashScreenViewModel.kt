package com.xelar.legayd.mathchildrengame.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.xelar.legayd.mathchildrengame.R

class SplashScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application


    private var _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean>
        get() = _state

    fun destination(flag: Boolean){
        _state.value = flag
    }
}