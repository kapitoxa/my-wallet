package ru.kapitoxa.mywallet.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kapitoxa.mywallet.database.Category
import ru.kapitoxa.mywallet.database.WalletDatabaseDao

class CategoriesViewModel(database: WalletDatabaseDao) : ViewModel() {

    val categories = database.getAllCategories()

    private val _navigateToCategoryDetail = MutableLiveData<Category?>()
    val navigateToCategoryDetail: LiveData<Category?>
        get() = _navigateToCategoryDetail

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
}