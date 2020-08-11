package ru.kapitoxa.mywallet.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_type")
data class CategoryType(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,

        var name: String
)