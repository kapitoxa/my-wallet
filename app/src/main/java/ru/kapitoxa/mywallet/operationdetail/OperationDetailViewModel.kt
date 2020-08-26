package ru.kapitoxa.mywallet.operationdetail

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kapitoxa.mywallet.database.Operation
import ru.kapitoxa.mywallet.database.WalletDatabaseDao
import java.text.DateFormat
import java.util.*

class OperationDetailViewModel(
        operation: Operation,
        private val database: WalletDatabaseDao,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _operation = MutableLiveData<Operation>()
    val operation: LiveData<Operation>
        get() = _operation

    val categories = database.getAllCategories()

    private val _operationDate = MutableLiveData<Long>()
    val operationDate = Transformations.map(_operationDate) {
        when (it) {
            0L -> null
            else -> DateFormat.getDateInstance().format(Date(it))
        }
    }

    private val _showDatePickerDialog = MutableLiveData<Boolean>()
    val showDatePickerDialog: LiveData<Boolean>
        get() = _showDatePickerDialog

    private val _showOperationNameFieldError = MutableLiveData<Boolean>()
    val showOperationNameFieldError: LiveData<Boolean>
        get() = _showOperationNameFieldError

    private val _showOperationDateFieldError = MutableLiveData<Boolean>()
    val showOperationDateFieldError: LiveData<Boolean>
        get() = _showOperationDateFieldError

    private val _navigateToOperations = MutableLiveData<Boolean>()
    val navigateToOperations: LiveData<Boolean>
        get() = _navigateToOperations

    init {
        _operation.value = operation
        setOperationDate(operation.operationDate)
    }

    fun setOperationDate(date: Long) {
        _operationDate.value = date

        val operation = _operation.value!!
        operation.operationDate = date
    }

    fun onOperationDateClicked() {
        _showDatePickerDialog.value = true
    }

    fun onShowedDatePickerDialog() {
        _showDatePickerDialog.value = false
    }

    fun onCategoryChecked(categoryId: Long, isChecked: Boolean) {
        val operation = _operation.value!!

        if (isChecked && operation.categoryId != categoryId) {
            operation.categoryId = categoryId
        }
    }

    fun onSave() {
        val operation = operation.value!!

        if (operation.isValid()) {
            viewModelScope.launch(defaultDispatcher) {
                operation.store()
            }
            _navigateToOperations.value = true
        }
    }

    fun onNavigatedToOperations() {
        _navigateToOperations.value = false
    }

    private fun Operation.isValid(): Boolean {
        var valid = true

        if (name.isEmpty()) {
            _showOperationNameFieldError.value = true
            valid = false
        } else {
            _showOperationNameFieldError.value = false
        }

        if (operationDate == 0L) {
            _showOperationDateFieldError.value = true
            valid = false
        } else {
            _showOperationDateFieldError.value = false
        }

        if (categoryId == 0L) {
            valid = false
        }

        return valid
    }

    private suspend fun Operation.store() {
        if (id == 0L) {
            database.insertOperation(this)
        } else {
            database.updateOperation(this)
        }
    }
}
