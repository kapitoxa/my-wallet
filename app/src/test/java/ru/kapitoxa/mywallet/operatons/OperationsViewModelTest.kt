package ru.kapitoxa.mywallet.operatons

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
import ru.kapitoxa.mywallet.database.Operation
import ru.kapitoxa.mywallet.database.OperationDetail
import ru.kapitoxa.mywallet.database.WalletDatabaseDao
import ru.kapitoxa.mywallet.operations.OperationsViewModel

@RunWith(RobolectricTestRunner::class)
class OperationsViewModelTest {

    private val operationsListLiveData = MutableLiveData<List<OperationDetail>>()

    private val operationsList = listOf(
            OperationDetail(
                    1L,
                    "Taxi",
                    1598227200000L,
                    100.0,
                    1L,
                    "Taxi",
                    1L
            ),
            OperationDetail(2L,
                    "Internet",
                    1598227200000L,
                    200.0,
                    2L,
                    "Internet",
                    2L
            )
    )

    private lateinit var viewModel: OperationsViewModel

    private lateinit var operationsLiveData: LiveData<List<OperationDetail>>

    private lateinit var navigateToOperationDetailLiveData: LiveData<Operation?>

    @Mock
    private lateinit var database: WalletDatabaseDao

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        operationsListLiveData.value = operationsList
        `when`(database.getAllOperationsDetail()).thenReturn(operationsListLiveData)

        viewModel = OperationsViewModel(database)
        operationsLiveData = viewModel.operations
        navigateToOperationDetailLiveData = viewModel.navigateToOperationDetail
    }

    @Test
    fun `fill operations list on initialization`() {
        Assert.assertEquals(operationsList, operationsLiveData.value)
    }

    @Test
    fun `navigate on fab clicked`() {
        Assert.assertNull("Navigation target is not null",
                navigateToOperationDetailLiveData.value)

        viewModel.onFabClicked()
        Assert.assertEquals("Navigation target is not default Operation",
                Operation(), navigateToOperationDetailLiveData.value)

        viewModel.onNavigatedToOperationDetail()
        Assert.assertNull("Navigation target not reset after navigation",
                navigateToOperationDetailLiveData.value)
    }

    @Test
    fun `navigate on category clicked`() {
        Assert.assertNull("Navigation target is not null",
                navigateToOperationDetailLiveData.value)

        viewModel.onOperationClicked(operationsList[0])

        val operationDetail = operationsList[0]
        val operation = Operation(
                operationDetail.id,
                operationDetail.name,
                operationDetail.operationDate,
                operationDetail.amount,
                operationDetail.categoryId
        )
        Assert.assertEquals("Navigation target is not clicked Operation",
                operation, navigateToOperationDetailLiveData.value)

        viewModel.onNavigatedToOperationDetail()
        Assert.assertNull("Navigation target not reset after navigation",
                navigateToOperationDetailLiveData.value)
    }
}