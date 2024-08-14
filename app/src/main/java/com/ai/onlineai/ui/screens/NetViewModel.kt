package com.ai.onlineai.ui.screens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ai.onlineai.OnlineAiClientApplication
import com.ai.onlineai.models.NetRequest
import com.ai.onlineai.repositry.NetRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NetViewModel(
    private val netRepository: NetRepository
) : ViewModel() {

    val states = mutableStateOf<AppState>(AppState.Loading)

    fun detectObject(data: NetRequest) {
        viewModelScope.launch {

            delay(11000)
            netRepository.detectObjects(data)?.let {
                Log.e("VIEEWMODEL", it.toString())
                states.value = AppState.Success(it)
            }
        }


    }

    companion object {
        val FACTORY: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as OnlineAiClientApplication)
                val netRepository = application.appContainer.netRepository
                NetViewModel(netRepository = netRepository)
            }
        }
    }
}