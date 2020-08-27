package ru.kapitoxa.mywallet.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WalletDatabaseDao {

    @Insert
    suspend fun insertCategoryType(type: CategoryType)

    @Insert
    suspend fun insertAllCategoryTypes(vararg type: CategoryType)

    @Insert
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Insert
    suspend fun insertOperation(operation: Operation)

    @Update
    suspend fun updateOperation(operation: Operation)

    @Query("select * from category_type where id = :key")
    suspend fun getCategoryType(key: Long): CategoryType?

    @Transaction
    @Query("select * from category where id = :key")
    suspend fun getCategory(key: Long): CategoryWithType?

    @Transaction
    @Query("select * from operation where id = :key")
    suspend fun getOperation(key: Long): Operation?

    @Transaction
    @Query("select * from category order by id")
    fun getAllCategories(): LiveData<List<CategoryWithType>>

    @Transaction
    @Query("select * from category_type order by id")
    fun getAllCategoryTypes(): LiveData<List<CategoryType>>

    @Query("select * from operation_detail")
    fun getAllOperationsDetail(): LiveData<List<OperationDetail>>
}