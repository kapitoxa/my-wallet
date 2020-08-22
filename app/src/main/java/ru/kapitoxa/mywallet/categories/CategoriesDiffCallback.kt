package ru.kapitoxa.mywallet.categories

import androidx.recyclerview.widget.DiffUtil
import ru.kapitoxa.mywallet.database.CategoryWithType

class CategoriesDiffCallback : DiffUtil.ItemCallback<CategoryWithType>() {
    override fun areItemsTheSame(oldItem: CategoryWithType, newItem: CategoryWithType): Boolean {
        return oldItem.category.id == newItem.category.id
    }

    override fun areContentsTheSame(oldItem: CategoryWithType, newItem: CategoryWithType): Boolean {
        return oldItem == newItem
    }
}