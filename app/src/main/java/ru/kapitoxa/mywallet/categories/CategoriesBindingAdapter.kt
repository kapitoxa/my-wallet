package ru.kapitoxa.mywallet.categories

import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.kapitoxa.mywallet.R
import ru.kapitoxa.mywallet.database.CategoryWithType
import java.util.*
import kotlin.math.abs

@BindingAdapter("categoryTypeColor")
fun TextView.bindCategoryTypeColor(item: CategoryWithType?) {
    item?.let {
        setTextColor(context.getColor(
                when (item.type.id) {
                    1L -> R.color.color_text_positive
                    else -> R.color.color_text_negative
                }
        ))
    }
}

@BindingAdapter("iconLetterText")
fun TextView.bindFirstLetter(item: CategoryWithType?) {
    item?.let {
        text = item.category.name.first().toString().toUpperCase(Locale.getDefault())
    }
}

@BindingAdapter("iconLetterBackground")
fun TextView.bindBackgroundDrawable(item: CategoryWithType?) {
    item?.let {
        val materialColors = context.resources.obtainTypedArray(R.array.material_colors)
        val colorIndex = abs(it.category.id.hashCode()) % materialColors.length()
        val color = materialColors.getColor(colorIndex, 0)

        val drawable = context.getDrawable(R.drawable.ic_shape_oval) as GradientDrawable
        drawable.mutate()
        drawable.colors = intArrayOf(color, color)

        background = drawable
    }
}