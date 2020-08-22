package ru.kapitoxa.mywallet.categorydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kapitoxa.mywallet.database.Category
import ru.kapitoxa.mywallet.database.CategoryType
import ru.kapitoxa.mywallet.database.WalletDatabaseDao

class CategoryDetailViewModel(
        category: Category,
        private val database: WalletDatabaseDao,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {

    private val _category = MutableLiveData<Category>()
    val category: MutableLiveData<Category>
        get() = _category

    private val _types = database.getAllCategoryTypes()
    val types: LiveData<List<CategoryType>>
        get() = _types

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
            viewModelScope.launch {
                val category = _category.value ?: return@launch
                if (category.id == 0L) {
                    insert(category)
                } else {
                    update(category)
                }
                _showCategoryNameFieldError.value = false
                _navigateToCategories.value = true
            }
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

    private suspend fun insert(category: Category) {
        withContext(defaultDispatcher) {
            database.insertCategory(category)
        }
    }

    private suspend fun update(category: Category) {
        withContext(defaultDispatcher) {
            database.updateCategory(category)
        }
    }
}