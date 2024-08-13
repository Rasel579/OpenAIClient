package com.ai.onlineai.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ai.onlineai.repositry.NetRepository
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import com.ai.onlineai.OnlineAiClientApplication
import com.ai.onlineai.models.NetModelResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NetViewModel(
    private val netRepository: NetRepository
): ViewModel() {

    fun detectObject(data: NetModelResponse ){
        viewModelScope.launch {
            delay(6000)
            Log.i("RESPONSE", netRepository.detectObjects(data)?.imageByteArray.toString())


        }

    }
    companion object{
        val FACTORY: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as OnlineAiClientApplication)
                val netRepository = application.appContainer.netRepository
                NetViewModel(netRepository = netRepository)
            }
        }
    }
}