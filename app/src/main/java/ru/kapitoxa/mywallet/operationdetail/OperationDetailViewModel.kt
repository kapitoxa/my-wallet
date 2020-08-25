package ru.kapitoxa.mywallet.operationdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.kapitoxa.mywallet.database.Operation
import ru.kapitoxa.mywallet.database.WalletDatabaseDao
import java.text.DateFormat
import java.util.*

class OperationDetailViewModel(
        operation: Operation,
        database: WalletDatabaseDao,
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
        Log.i("OperationDetailViewModel", "Category chip $categoryId change state to $isChecked")
        val operation = _operation.value!!

        if (isChecked && operation.categoryId != categoryId) {
            operation.categoryId = categoryId
        }
    }
}
