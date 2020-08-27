package ru.kapitoxa.mywallet

import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.*
import kotlin.math.abs

@BindingAdapter("iconLetterBackground")
fun TextView.bindBackgroundDrawable(id: Long?) {
    id?.let {
        val materialColors = context.resources.obtainTypedArray(R.array.material_colors)
        val colorIndex = abs(it.hashCode()) % materialColors.length()
        val color = materialColors.getColor(colorIndex, 0)
        materialColors.recycle()

        val drawable = context.getDrawable(R.drawable.ic_shape_oval) as GradientDrawable
        drawable.mutate()
        drawable.colors = intArrayOf(color, color)

        background = drawable
    }
}

@BindingAdapter("iconLetterText")
fun TextView.bindFirstLetter(word: String?) {
    word?.let {
        text = word.first().toString().toUpperCase(Locale.getDefault())
    }
}