package ru.kapitoxa.mywallet.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoriesViewModel : ViewModel() {
    private val _navigateToCategoryDetail = MutableLiveData<Boolean>()
    val navigateToCategoryDetail: LiveData<Boolean>
        get() = _navigateToCategoryDetail

    fun navigateToCategoryDetail() {
        _navigateToCategoryDetail.value = true
    }

    fun onNavigatedToCategoryDetail() {
        _navigateToCategoryDetail.value = false
    }
}