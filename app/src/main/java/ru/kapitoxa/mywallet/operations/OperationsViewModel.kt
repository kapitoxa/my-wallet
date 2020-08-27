package ru.kapitoxa.mywallet.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kapitoxa.mywallet.database.Operation
import ru.kapitoxa.mywallet.database.OperationDetail
import ru.kapitoxa.mywallet.database.WalletDatabaseDao

class OperationsViewModel(database: WalletDatabaseDao) : ViewModel() {

    val operations = database.getAllOperationsDetail()

    private val _navigateToOperationDetail = MutableLiveData<Operation?>()
    val navigateToOperationDetail: LiveData<Operation?>
        get() = _navigateToOperationDetail

    fun onFabClicked() {
        navigateToOperationDetail(Operation())
    }

    fun onOperationClicked(operationDetail: OperationDetail) {
        val operation = Operation(
                operationDetail.id,
                operationDetail.name,
                operationDetail.operationDate,
                operationDetail.amount,
                operationDetail.categoryId
        )
        navigateToOperationDetail(operation)
    }

    fun onNavigatedToOperationDetail() {
        _navigateToOperationDetail.value = null
    }

    private fun navigateToOperationDetail(operation: Operation) {
        _navigateToOperationDetail.value = operation
    }
}
