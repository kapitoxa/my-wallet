package ru.kapitoxa.mywallet.database

import androidx.room.*

@Entity(
        tableName = "operation",
        foreignKeys = [
            ForeignKey(
                    entity = Category::class,
                    parentColumns = ["id"],
                    childColumns = ["category_id"]
            )
        ],
        indices = [Index("category_id")]
)
data class Operation(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,

        var name: String = "",

        @ColumnInfo(name = "operation_date")
        var operationDate: Long = 0L,

        var amount: Double = 0.0,

        @ColumnInfo(name = "category_id")
        var categoryId: Long = 0L
)