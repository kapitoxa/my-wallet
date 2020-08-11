package ru.kapitoxa.mywallet.database

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithOperations(
        @Embedded
        val category: Category,

        @Relation(
                parentColumn = "id",
                entityColumn = "category_id"
        )
        val operations: List<Operation>

)