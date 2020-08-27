package ru.kapitoxa.mywallet.operations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kapitoxa.mywallet.database.OperationDetail
import ru.kapitoxa.mywallet.databinding.ItemOperationBinding

class OperationsAdapter(private val clickListener: OperationsListener)
    : ListAdapter<OperationDetail, OperationsAdapter.ViewHolder>(OperationsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: ItemOperationBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OperationDetail, clickListener: OperationsListener) {
            binding.operation = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemOperationBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
