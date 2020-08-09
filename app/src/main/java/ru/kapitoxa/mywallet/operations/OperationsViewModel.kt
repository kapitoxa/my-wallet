package ru.kapitoxa.mywallet.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OperationsViewModel : ViewModel() {
    private val _navigateToOperationDetail = MutableLiveData<Boolean>()
    val navigateToOperationDetail: LiveData<Boolean>
        get() = _navigateToOperationDetail

    fun navigateToOperationDetail() {
        _navigateToOperationDetail.value = true
    }

    fun onNavigatedToOperationDetail() {
        _navigateToOperationDetail.value = false
    }
}