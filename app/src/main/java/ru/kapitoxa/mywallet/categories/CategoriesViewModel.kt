package ru.kapitoxa.mywallet.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kapitoxa.mywallet.database.Category
import ru.kapitoxa.mywallet.database.CategoryWithType
import ru.kapitoxa.mywallet.database.WalletDatabaseDao

class CategoriesViewModel(
        private val database: WalletDatabaseDao,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {

    private val _categories = MutableLiveData<List<CategoryWithType>>()
    val categories: LiveData<List<CategoryWithType>>
        get() = _categories

    private val _navigateToCategoryDetail = MutableLiveData<Category?>()
    val navigateToCategoryDetail: LiveData<Category?>
        get() = _navigateToCategoryDetail

    init {
        initializeCategories()
    }

    fun onFabClicked() {
        navigateToCategoryDetail(Category())
    }

    fun onCategoryClicked(category: Category) {
        navigateToCategoryDetail(category)
    }

    fun onNavigatedToCategoryDetail() {
        _navigateToCategoryDetail.value = null
    }

    private fun navigateToCategoryDetail(category: Category) {
        _navigateToCategoryDetail.value = category
    }

    private fun initializeCategories() {
        viewModelScope.launch {
            _categories.postValue(getCategoriesFromDatabase())
        }
    }

    private suspend fun getCategoriesFromDatabase(): List<CategoryWithType> {
        return withContext(defaultDispatcher) {
            val categories = database.getAllCategories()
            categories
        }
    }
}