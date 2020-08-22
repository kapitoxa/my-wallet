package ru.kapitoxa.mywallet.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kapitoxa.mywallet.database.Category
import ru.kapitoxa.mywallet.database.CategoryWithType
import ru.kapitoxa.mywallet.databinding.ItemCategoryBinding

class CategoriesAdapter(private val clickListener: CategoriesListener)
    : ListAdapter<CategoryWithType, CategoriesAdapter.ViewHolder>(CategoriesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }


    class ViewHolder private constructor(val binding: ItemCategoryBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoryWithType, clickListener: CategoriesListener) {
            binding.categoryWithType = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCategoryBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class CategoriesListener(val clickListener: (category: Category) -> Unit) {
    fun onClick(category: Category) = clickListener(category)
}