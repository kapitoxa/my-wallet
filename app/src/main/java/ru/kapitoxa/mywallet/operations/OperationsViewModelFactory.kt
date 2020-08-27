package ru.kapitoxa.mywallet.operations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kapitoxa.mywallet.database.WalletDatabaseDao

class OperationsViewModelFactory(val database: WalletDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OperationsViewModel::class.java)) {
            return OperationsViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
