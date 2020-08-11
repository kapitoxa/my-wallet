package ru.kapitoxa.mywallet.database

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class WalletDatabaseTest {

    private lateinit var walletDatabaseDao: WalletDatabaseDao
    private lateinit var database: WalletDatabase

    @Before
    fun createAndPrePopulateDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, WalletDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        walletDatabaseDao = database.walletDatabaseDao

        val types = WalletTestHelper.createArrayOfCategoryTypes()
        walletDatabaseDao.insertAllCategoryTypes(*types)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun readCategoryType() {
        val expected = WalletTestHelper.createCategoryType(1L)
        val fromDb = walletDatabaseDao.getCategoryType(expected.id)

        Assert.assertEquals(expected, fromDb)
    }

    @Test
    @Throws(Exception::class)
    fun readAllCategoryTypes() {
        val types = WalletTestHelper.createArrayOfCategoryTypes()
        val fromDb = walletDatabaseDao.getAllCategoryTypes().blockingValue

        Assert.assertEquals(types.asList(), fromDb)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadCategory() {
        val category = Category(1, "Food", 2)
        val expected = WalletTestHelper.convertToCategoryType(category)

        walletDatabaseDao.insertCategory(category)
        val fromDb = walletDatabaseDao.getCategory(category.id)

        Assert.assertEquals(expected, fromDb)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadAllCategories() {
        val category1 = Category(1, "Food", 2)
        val category2 = Category(2, "Salary", 1)

        walletDatabaseDao.insertCategory(category1)
        walletDatabaseDao.insertCategory(category2)

        val expected = listOf(
                WalletTestHelper.convertToCategoryType(category1),
                WalletTestHelper.convertToCategoryType(category2)
        )

        val fromDb = walletDatabaseDao.getAllCategories().blockingValue

        Assert.assertEquals(expected, fromDb)
    }

    @Test
    @Throws(Exception::class)
    fun updateCategory() {
        val category = Category(1, "Food", 2)
        walletDatabaseDao.insertCategory(category)

        category.name = "Rent"
        walletDatabaseDao.updateCategory(category)

        val expected = WalletTestHelper.convertToCategoryType(category)
        val fromDb = walletDatabaseDao.getCategory(category.id)

        Assert.assertEquals(expected, fromDb)
    }

    @Test(expected = SQLiteConstraintException::class)
    @Throws(Exception::class)
    fun writeCategoryWithNonExistingType() {
        val category = Category(1, "Food", 3)
        walletDatabaseDao.insertCategory(category)
    }

    @Test(expected = SQLiteConstraintException::class)
    @Throws(Exception::class)
    fun writeOperationWithNonExistingCategory() {
        val operation = Operation(1, "Weekly restocking", System.currentTimeMillis(), 1)
        walletDatabaseDao.insertOperation(operation)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadOperation() {
        val category = Category(1, "Food", 2)
        walletDatabaseDao.insertCategory(category)

        val operation = Operation(1, "Weekly restocking", System.currentTimeMillis(), 1)
        walletDatabaseDao.insertOperation(operation)

        val fromDb = walletDatabaseDao.getOperation(operation.id)

        Assert.assertEquals(operation, fromDb)
    }

    @Test
    @Throws(Exception::class)
    fun updateOperation() {
        val category = Category(1, "Food", 2)
        walletDatabaseDao.insertCategory(category)

        val operation = Operation(1, "Weekly restocking", System.currentTimeMillis(), 1)
        walletDatabaseDao.insertOperation(operation)

        operation.name = "Morning coffee"
        walletDatabaseDao.updateOperation(operation)

        val fromDb = walletDatabaseDao.getOperation(operation.id)

        Assert.assertEquals(operation, fromDb)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadAllOperations() {
        val category = Category(1, "Food", 2)
        walletDatabaseDao.insertCategory(category)

        val operation1 = Operation(1, "Weekly restocking", System.currentTimeMillis(), 1)
        val operation2 = Operation(2, "Morning coffee", System.currentTimeMillis(), 1)

        walletDatabaseDao.insertOperation(operation1)
        walletDatabaseDao.insertOperation(operation2)

        val expected = listOf(
                operation2,
                operation1
        )

        val fromDb = walletDatabaseDao.getAllOperations().blockingValue

        Assert.assertEquals(expected, fromDb)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadCategoryWithAllOperations() {
        val category = Category(1, "Food", 2)
        walletDatabaseDao.insertCategory(category)

        val operation1 = Operation(1, "Weekly restocking", System.currentTimeMillis(), 1)
        val operation2 = Operation(2, "Morning coffee", System.currentTimeMillis(), 1)

        walletDatabaseDao.insertOperation(operation1)
        walletDatabaseDao.insertOperation(operation2)

        val expected = listOf(
                WalletTestHelper.createCategoryWithOperations(category, operation1, operation2)
        )

        val fromDb = walletDatabaseDao.getCategoryWithAllOperations().blockingValue

        Assert.assertEquals(expected, fromDb)
    }

    class WalletTestHelper {
        companion object {
            fun createArrayOfCategoryTypes(): Array<CategoryType> {
                return arrayOf(
                        CategoryType(1, "Income"),
                        CategoryType(2, "Expense")
                )
            }

            @Throws(IllegalArgumentException::class)
            fun createCategoryType(id: Long): CategoryType {
                return when (id) {
                    1L -> CategoryType(1, "Income")
                    2L -> CategoryType(2, "Expense")
                    else -> throw IllegalArgumentException("Category type with id $id does not exists")
                }
            }

            fun convertToCategoryType(category: Category): CategoryWithType {
                val categoryType = createCategoryType(category.typeId)
                return CategoryWithType(category = category, type = categoryType)
            }

            fun createCategoryWithOperations(category: Category, vararg operations: Operation)
                    : CategoryWithOperations {
                return CategoryWithOperations(category = category, operations = operations.asList())
            }
        }
    }

    /**
     * LiveData extension property for use in unit tests
     */
    private val <T> LiveData<T>.blockingValue: T?
        get() {
            var value: T? = null
            val latch = CountDownLatch(1)
            GlobalScope.launch(Dispatchers.Main) {
                observeForever {
                    value = it
                    latch.countDown()
                }
            }
            return if (latch.await(2, TimeUnit.SECONDS)) {
                value
            } else {
                throw Exception("LiveData value was not set within 2 seconds")
            }
        }
}