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
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.kapitoxa.mywallet.R
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class WalletDatabaseTest {

    private lateinit var walletDatabaseDao: WalletDatabaseDao
    private lateinit var database: WalletDatabase
    private lateinit var context: Context

    @Before
    fun createAndPrePopulateDb() = runBlocking {
        context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(context, WalletDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        walletDatabaseDao = database.walletDatabaseDao
        WalletDatabase.Helper.populate(context, walletDatabaseDao)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun readCategoryType() = runBlocking {
        val expected = Helper.createCategoryType(context, 1L)
        val fromDb = walletDatabaseDao.getCategoryType(expected.id)

        Assert.assertEquals(expected, fromDb)
    }

    @Test
    fun readAllCategoryTypes() = runBlocking {
        val types = Helper.createArrayOfCategoryTypes(context)
        val fromDb = walletDatabaseDao.getAllCategoryTypes().blockingValue

        Assert.assertEquals(types.asList(), fromDb)
    }

    @Test
    fun writeAndReadCategory() = runBlocking {
        val category = Category(1, "Food", 2)
        val expected = Helper.convertToCategoryType(context, category)

        walletDatabaseDao.insertCategory(category)
        val fromDb = walletDatabaseDao.getCategory(category.id)

        Assert.assertEquals(expected, fromDb)
    }

    @Test
    fun writeAndReadAllCategories() = runBlocking {
        val category1 = Category(1, "Food", 2)
        val category2 = Category(2, "Salary", 1)

        walletDatabaseDao.insertCategory(category1)
        walletDatabaseDao.insertCategory(category2)

        val expected = listOf(
                Helper.convertToCategoryType(context, category1),
                Helper.convertToCategoryType(context, category2)
        )

        val fromDb = walletDatabaseDao.getAllCategories().blockingValue

        Assert.assertEquals(expected, fromDb)
    }

    @Test
    fun updateCategory() = runBlocking {
        val category = Category(1, "Food", 2)
        walletDatabaseDao.insertCategory(category)

        category.name = "Rent"
        walletDatabaseDao.updateCategory(category)

        val expected = Helper.convertToCategoryType(context, category)
        val fromDb = walletDatabaseDao.getCategory(category.id)

        Assert.assertEquals(expected, fromDb)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun writeCategoryWithNonExistingType() = runBlocking {
        val category = Category(1, "Food", 3)
        walletDatabaseDao.insertCategory(category)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun writeOperationWithNonExistingCategory() = runBlocking {
        val operation = Operation(1, "Weekly restocking", System.currentTimeMillis(),
                0.0, 1)
        walletDatabaseDao.insertOperation(operation)
    }

    @Test
    fun writeAndReadOperation() = runBlocking {
        val category = Category(1, "Food", 2)
        walletDatabaseDao.insertCategory(category)

        val operation = Operation(1, "Weekly restocking", System.currentTimeMillis(),
                0.0, 1)
        walletDatabaseDao.insertOperation(operation)

        val fromDb = walletDatabaseDao.getOperation(operation.id)

        Assert.assertEquals(operation, fromDb)
    }

    @Test
    fun updateOperation() = runBlocking {
        val category = Category(1, "Food", 2)
        walletDatabaseDao.insertCategory(category)

        val operation = Operation(1, "Weekly restocking", System.currentTimeMillis(),
                0.0, 1)
        walletDatabaseDao.insertOperation(operation)

        operation.name = "Morning coffee"
        walletDatabaseDao.updateOperation(operation)

        val fromDb = walletDatabaseDao.getOperation(operation.id)

        Assert.assertEquals(operation, fromDb)
    }

    @Test
    fun readOperationDetailView() = runBlocking {
        val category = Category(1, "Food", 2)
        walletDatabaseDao.insertCategory(category)

        val operation = Operation(1, "Weekly restocking", System.currentTimeMillis(),
                36.92, 1)
        walletDatabaseDao.insertOperation(operation)

        val expected = listOf(
                OperationDetail(
                        operation.id,
                        operation.name,
                        operation.operationDate,
                        operation.amount,
                        operation.categoryId,
                        category.name,
                        category.typeId
                )
        )

        val fromDb = walletDatabaseDao.getAllOperationsDetail().blockingValue

        Assert.assertEquals(expected, fromDb)
    }

    class Helper {
        companion object {
            fun createArrayOfCategoryTypes(context: Context): Array<CategoryType> {
                return arrayOf(
                        createCategoryType(context, 1),
                        createCategoryType(context, 2)
                )
            }

            @Throws(IllegalArgumentException::class)
            fun createCategoryType(context: Context, id: Long): CategoryType {
                return when (id) {
                    1L -> CategoryType(1, context.getString(R.string.category_type_income))
                    2L -> CategoryType(2, context.getString(R.string.category_type_expense))
                    else -> throw IllegalArgumentException("Category type with id $id does not exists")
                }
            }

            fun convertToCategoryType(context: Context, category: Category): CategoryWithType {
                val categoryType = createCategoryType(context, category.typeId)
                return CategoryWithType(category = category, type = categoryType)
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