package ru.kapitoxa.mywallet.operationdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import ru.kapitoxa.mywallet.blockingValue
import ru.kapitoxa.mywallet.database.*

@RunWith(RobolectricTestRunner::class)
class OperationDetailViewModelTest {

    private lateinit var viewModel: OperationDetailViewModel

    private lateinit var operationLiveData: LiveData<Operation>

    private lateinit var categoriesLiveData: LiveData<List<CategoryWithType>>

    private lateinit var operationDateLiveData: LiveData<String?>

    private lateinit var showDatePickerDialogLiveData: LiveData<Boolean>

    @Mock
    private lateinit var database: WalletDatabaseDao

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private val categoriesWithTypeLiveData: MutableLiveData<List<CategoryWithType>> = MutableLiveData()

    private val categoriesWithTypeList = listOf(
            CategoryWithType(
                    category = Category(1L, "Food", 2L),
                    type = CategoryType(2L, "Expense")
            ),
            CategoryWithType(
                    category = Category(2L, "Salary", 1L),
                    type = CategoryType(1L, "Income")
            )
    )

    @Before
    fun setUp() {
        categoriesWithTypeLiveData.value = categoriesWithTypeList
        `when`(database.getAllCategories()).thenReturn(categoriesWithTypeLiveData)
        viewModel = OperationDetailViewModel(Operation(), database)

        operationLiveData = viewModel.operation
        categoriesLiveData = viewModel.categories
        operationDateLiveData = viewModel.operationDate
        showDatePickerDialogLiveData = viewModel.showDatePickerDialog
    }

    @Test
    fun initWithEmptyCategory() {
        val expected = Operation()
        Assert.assertEquals(expected, operationLiveData.value)
        Assert.assertNull(operationDateLiveData.value)
    }

    @Test
    fun categoriesInitialization() {
        Assert.assertEquals(categoriesWithTypeList, categoriesLiveData.value)
    }

    fun setDefaultOperationDate() {
        val timestamp = 0L
        viewModel.setOperationDate(timestamp)

        Assert.assertNull("Operation date LiveData not updated",
                operationDateLiveData.blockingValue)
        Assert.assertEquals("Operation not updated",
                timestamp, operationLiveData.value!!.operationDate)
    }

    @Test
    fun setOperationDate() {
        val timestamp = 1598227200000L
        viewModel.setOperationDate(timestamp)

        Assert.assertEquals("Operation date LiveData not updated",
                "Aug 24, 2020",
                operationDateLiveData.blockingValue)

        Assert.assertEquals("Operation not updated",
                timestamp, operationLiveData.value!!.operationDate)
    }

    @Test
    fun showDatePicker() {
        viewModel.onOperationDateClicked()
        Assert.assertTrue(showDatePickerDialogLiveData.value!!)

        viewModel.onShowedDatePickerDialog()
        Assert.assertFalse(showDatePickerDialogLiveData.value!!)
    }

    @Test
    fun checkCategory() {
        viewModel.onCategoryChecked(1, true)
        Assert.assertEquals(1, operationLiveData.value!!.categoryId)

        viewModel.onCategoryChecked(2, true)
        Assert.assertEquals(2, operationLiveData.value!!.categoryId)

        viewModel.onCategoryChecked(1, false)
        Assert.assertEquals(2, operationLiveData.value!!.categoryId)
    }
}