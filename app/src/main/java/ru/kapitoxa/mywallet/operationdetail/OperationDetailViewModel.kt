package ru.kapitoxa.mywallet.operationdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kapitoxa.mywallet.database.Operation
import ru.kapitoxa.mywallet.database.WalletDatabaseDao

class OperationDetailViewModel(
        operation: Operation,
        database: WalletDatabaseDao,
) : ViewModel() {

    private val _operation = MutableLiveData<Operation>()
    val operation: LiveData<Operation>
        get() = _operation

    val categories = database.getAllCategories()

    init {
        _operation.value = operation
    }
}
