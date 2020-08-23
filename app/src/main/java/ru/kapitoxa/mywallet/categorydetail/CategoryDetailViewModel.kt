package ru.kapitoxa.mywallet.categorydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kapitoxa.mywallet.database.Category
import ru.kapitoxa.mywallet.database.WalletDatabaseDao

class CategoryDetailViewModel(
        category: Category,
        private val database: WalletDatabaseDao,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {

    private val _category = MutableLiveData<Category>()
    val category: MutableLiveData<Category>
        get() = _category

    val types = database.getAllCategoryTypes()

    private val _showCategoryNameFieldError = MutableLiveData<Boolean>()
    val showCategoryNameFieldError: LiveData<Boolean>
        get() = _showCategoryNameFieldError

    private val _navigateToCategories = MutableLiveData<Boolean>()
    val navigateToCategories: LiveData<Boolean>
        get() = _navigateToCategories

    init {
        _category.value = category
    }

    fun onTypeChanged(typeId: Long, isChecked: Boolean) {
        val category = _category.value!!

        if (isChecked && typeId != category.typeId) {
            category.typeId = typeId
        }
    }

    fun onSave() {
        if (isValid()) {
            viewModelScope.launch(defaultDispatcher) {
                val category = _category.value ?: return@launch
                if (category.id == 0L) {
                    database.insertCategory(category)
                } else {
                    database.updateCategory(category)
                }
            }
            _showCategoryNameFieldError.value = false
            _navigateToCategories.value = true
        } else {
            _showCategoryNameFieldError.value = true
        }
    }

    fun onNavigatedToCategories() {
        _navigateToCategories.value = false
    }

    private fun isValid(): Boolean {
        val category = _category.value!!
        return category.name.isNotEmpty() && category.typeId > 0
    }
}