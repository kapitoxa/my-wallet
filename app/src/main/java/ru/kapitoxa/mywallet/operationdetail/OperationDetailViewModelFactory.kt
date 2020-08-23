package ru.kapitoxa.mywallet.operationdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kapitoxa.mywallet.database.Operation
import ru.kapitoxa.mywallet.database.WalletDatabaseDao

class OperationDetailViewModelFactory(
        private val operation: Operation,
        private val database: WalletDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OperationDetailViewModel::class.java)) {
            return OperationDetailViewModel(operation, database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
