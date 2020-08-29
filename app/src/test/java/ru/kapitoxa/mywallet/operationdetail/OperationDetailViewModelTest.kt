package ru.kapitoxa.mywallet.operationdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.datepicker.MaterialDatePicker
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

    private lateinit var showDatePickerDialogLiveData: LiveData<Long?>

    private lateinit var showOperationNameFieldErrorLiveData: LiveData<Boolean>

    private lateinit var showOperationDateFieldErrorLiveData: LiveData<Boolean>

    private lateinit var navigateToOperationsLiveData: LiveData<Boolean>

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
        showOperationNameFieldErrorLiveData = viewModel.showOperationNameFieldError
        showOperationDateFieldErrorLiveData = viewModel.showOperationDateFieldError
        navigateToOperationsLiveData = viewModel.navigateToOperations
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
        Assert.assertEquals(MaterialDatePicker.todayInUtcMilliseconds(),
                showDatePickerDialogLiveData.value)

        viewModel.onShowedDatePickerDialog()
        Assert.assertNull(showDatePickerDialogLiveData.value)
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

    @Test
    fun `success saving and navigate to operations`() {
        val operation = operationLiveData.value!!
        operation.name = "Some operation"
        operation.operationDate = 1598227200000L
        operation.categoryId = 1L

        viewModel.onSave()

        Assert.assertFalse("The error is displayed in the field Name",
                showOperationNameFieldErrorLiveData.value!!)
        Assert.assertFalse("The error is displayed in the field OperationData",
                showOperationDateFieldErrorLiveData.value!!)
        Assert.assertTrue("Navigation is not triggered",
                navigateToOperationsLiveData.value!!)

        viewModel.onNavigatedToOperations()

        Assert.assertFalse("Navigation in not canceled",
                navigateToOperationsLiveData.value!!)
    }

    @Test
    fun `show errors on save empty operation`() {
        operationLiveData.value!!

        viewModel.onSave()

        Assert.assertTrue("The error is not displayed in the field Name",
                showOperationNameFieldErrorLiveData.value!!)
        Assert.assertTrue("The error is not displayed in the field OperationDate",
                showOperationDateFieldErrorLiveData.value!!)
        Assert.assertEquals("Navigation is triggered",
                null, navigateToOperationsLiveData.value)
    }
}