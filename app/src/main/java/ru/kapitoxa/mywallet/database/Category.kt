package ru.kapitoxa.mywallet.database

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
        tableName = "category",
        foreignKeys = [
            ForeignKey(
                    entity = CategoryType::class,
                    parentColumns = ["id"],
                    childColumns = ["type_id"])
        ],
        indices = [Index("type_id")]
)
data class Category(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,

        var name: String = "",

        @ColumnInfo(name = "type_id")
        var typeId: Long = 0L
) : Parcelable