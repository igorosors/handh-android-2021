package com.example.lesson_10_strelyukhin.presentation

import androidx.lifecycle.*
import com.example.lesson_10_strelyukhin.data.LoadingState
import com.example.lesson_10_strelyukhin.data.remote.BridgesApi
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivityViewModel : ViewModel() {
    private val _loadingStateLiveData = MutableLiveData<LoadingState>()
    val loadingStateLiveData: LiveData<LoadingState> = _loadingStateLiveData

    fun loadBridges() {
        viewModelScope.launch {
            try {
                _loadingStateLiveData.postValue(LoadingState.Loading())
                val bridges = BridgesApi.apiService.getBridges()
                _loadingStateLiveData.postValue(LoadingState.Data(bridges))
            } catch (e: Exception) {
                _loadingStateLiveData.postValue(LoadingState.Error(e))
            }
        }
    }
}