package ru.kapitoxa.mywallet.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kapitoxa.mywallet.database.WalletDatabaseDao

class CategoriesViewModelFactory(private val database: WalletDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoriesViewModel::class.java)) {
            return CategoriesViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}