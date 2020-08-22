package ru.kapitoxa.mywallet.categories

import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.kapitoxa.mywallet.R
import ru.kapitoxa.mywallet.database.CategoryWithType
import java.util.*
import kotlin.random.Random

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
        val drawable = context.getDrawable(R.drawable.ic_shape_oval) as GradientDrawable
        drawable.mutate()
        drawable.colors = MaterialColors.getRandomColor()

        background = drawable
    }
}

class MaterialColors {
    companion object {
        private val colors = listOf(
                0xffe57373,
                0xfff06292,
                0xffba68c8,
                0xff9575cd,
                0xff7986cb,
                0xff64b5f6,
                0xff4fc3f7,
                0xff4dd0e1,
                0xff4db6ac,
                0xff81c784,
                0xffaed581,
                0xffff8a65,
                0xffd4e157,
                0xffffd54f,
                0xffffb74d,
                0xffa1887f,
                0xff90a4ae
        )

        fun getRandomColor(): IntArray {
            val random = Random.nextInt(colors.size)
            val color = colors[random].toInt()
            val arr = IntArray(2)
            arr[0] = color
            arr[1] = color
            return arr
        }
    }
}