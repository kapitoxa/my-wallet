package ru.kapitoxa.mywallet.database

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithType(
        @Embedded
        val category: Category,

        @Relation(
                parentColumn = "type_id",
                entityColumn = "id"
        )
        val type: CategoryType

)