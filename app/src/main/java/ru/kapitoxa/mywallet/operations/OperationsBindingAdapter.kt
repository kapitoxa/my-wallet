package ru.kapitoxa.mywallet.operations

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.kapitoxa.mywallet.R
import ru.kapitoxa.mywallet.database.OperationDetail
import java.text.DecimalFormat

@BindingAdapter("operationAmount")
fun TextView.bindOperationAmount(operationDetail: OperationDetail?) {
    operationDetail?.let {
        val decimalFormat = DecimalFormat()
        decimalFormat.minimumFractionDigits = 2
        decimalFormat.maximumFractionDigits = 2

        text = context.getString(
                when (it.typeId) {
                    1L -> R.string.item_operation_amount_income
                    else -> R.string.item_operation_amount_expense
                },
                decimalFormat.format(it.amount)
        )
    }
}

@BindingAdapter("operationAmountTextColor")
fun TextView.bindOperationAmountTextColor(operationDetail: OperationDetail?) {
    operationDetail?.let {
        setTextColor(context.getColor(
                when (it.typeId) {
                    1L -> R.color.color_text_positive
                    else -> R.color.color_on_surface
                }
        ))
    }
}