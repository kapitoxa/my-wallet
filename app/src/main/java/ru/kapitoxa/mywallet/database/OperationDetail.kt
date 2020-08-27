package ru.kapitoxa.mywallet.database

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
        viewName = "operation_detail",
        value = "select operation.id, operation.name, operation.operation_date, operation.amount, " +
                "operation.category_id, category.name as category_name, category_type.id as type_id " +
                "from operation " +
                "inner join category on operation.category_id = category.id " +
                "inner join category_type on category.type_id = category_type.id " +
                "order by operation.operation_date desc, operation.id"
)
data class OperationDetail(
        val id: Long,

        val name: String,

        @ColumnInfo(name = "operation_date")
        val operationDate: Long,

        val amount: Double,

        @ColumnInfo(name = "category_id")
        val categoryId: Long,

        @ColumnInfo(name = "category_name")
        val categoryName: String,

        @ColumnInfo(name = "type_id")
        val typeId: Long
)