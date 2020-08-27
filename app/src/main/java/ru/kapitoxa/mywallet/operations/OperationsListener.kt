package ru.kapitoxa.mywallet.operations

import ru.kapitoxa.mywallet.database.OperationDetail

class OperationsListener(val clickListener: (operationDetail: OperationDetail) -> Unit) {
    fun onClick(operationDetail: OperationDetail) = clickListener(operationDetail)
}