package ru.kapitoxa.mywallet.operations

import androidx.recyclerview.widget.DiffUtil
import ru.kapitoxa.mywallet.database.OperationDetail

class OperationsDiffCallback : DiffUtil.ItemCallback<OperationDetail>() {
    override fun areItemsTheSame(oldItem: OperationDetail, newItem: OperationDetail): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OperationDetail, newItem: OperationDetail): Boolean {
        return oldItem == newItem
    }
}
