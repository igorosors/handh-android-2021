package com.example.lesson_7_strelyukhin.presentation.list

import android.util.Log
import androidx.lifecycle.*
import com.example.lesson_7_strelyukhin.data.LoadingState
import com.example.lesson_7_strelyukhin.data.model.Bridge
import com.example.lesson_7_strelyukhin.data.remote.BridgesApi
import kotlinx.coroutines.launch
import java.lang.Exception

class FragmentListViewModel : ViewModel() {

    private val _bridgeListStateLiveData = MutableLiveData<LoadingState<List<Bridge>>>()
    val bridgeListStateLiveData: LiveData<LoadingState<List<Bridge>>> = _bridgeListStateLiveData
    fun loadBridges() {
        viewModelScope.launch {
            try {
                _bridgeListStateLiveData.postValue(LoadingState.Loading())
                if ((_bridgeListStateLiveData.value as? LoadingState.Data)?.data == null) {
                    val bridges = BridgesApi.apiService.getBridges()
                    _bridgeListStateLiveData.postValue(LoadingState.Data(bridges))
                } else {
                    _bridgeListStateLiveData.postValue(LoadingState.Data(
                        (_bridgeListStateLiveData.value as? LoadingState.Data)?.data!!
                    ))
                }
            } catch (e: Exception) {
                _bridgeListStateLiveData.postValue(LoadingState.Error(e))
            }
        }
    }

    fun changeBellState(idToReplace: Int) {
        var i = 0
        (_bridgeListStateLiveData.value as? LoadingState.Data)?.data?.forEach { bridge ->
            if (bridge.id == idToReplace) {
                if (bridge.isBell) {
                    val list = (_bridgeListStateLiveData.value as? LoadingState.Data)?.data?.toMutableList()
                    if (list != null) {
                        list[i] = bridge.copy(isBell = false)
                        _bridgeListStateLiveData.postValue(LoadingState.Data(list as List<Bridge>))
                    }
                } else {
                    val list = (_bridgeListStateLiveData.value as? LoadingState.Data)?.data?.toMutableList()
                    if (list != null) {
                        list[i] = bridge.copy(isBell = true)
                        _bridgeListStateLiveData.postValue(LoadingState.Data(list as List<Bridge>))
                    }
                }
            }
            i++
        }
    }
}