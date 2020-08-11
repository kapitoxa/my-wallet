package ru.kapitoxa.mywallet.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WalletDatabaseDao {

    @Insert
    fun insertCategoryType(type: CategoryType)

    @Insert
    fun insertAllCategoryTypes(vararg type: CategoryType)

    @Insert
    fun insertCategory(category: Category)

    @Update
    fun updateCategory(category: Category)

    @Insert
    fun insertOperation(operation: Operation)

    @Update
    fun updateOperation(operation: Operation)

    @Query("select * from category_type where id = :key")
    fun getCategoryType(key: Long): CategoryType?

    @Transaction
    @Query("select * from category where id = :key")
    fun getCategory(key: Long): CategoryWithType?

    @Transaction
    @Query("select * from operation where id = :key")
    fun getOperation(key: Long): Operation?

    @Transaction
    @Query("select * from category order by id")
    fun getAllCategories(): LiveData<List<CategoryWithType>>

    @Transaction
    @Query("select * from category_type order by id")
    fun getAllCategoryTypes(): LiveData<List<CategoryType>>

    @Transaction
    @Query("select * from operation order by id desc")
    fun getAllOperations(): LiveData<List<Operation>>

    @Transaction
    @Query("select * from category")
    fun getCategoryWithAllOperations(): LiveData<List<CategoryWithOperations>>
}