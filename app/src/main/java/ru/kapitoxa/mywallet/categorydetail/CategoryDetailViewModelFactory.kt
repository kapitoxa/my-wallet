package ru.kapitoxa.mywallet.categorydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kapitoxa.mywallet.database.Category
import ru.kapitoxa.mywallet.database.WalletDatabaseDao

class CategoryDetailViewModelFactory(private val category: Category,
                                     private val database: WalletDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryDetailViewModel::class.java)) {
            return CategoryDetailViewModel(category, database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}