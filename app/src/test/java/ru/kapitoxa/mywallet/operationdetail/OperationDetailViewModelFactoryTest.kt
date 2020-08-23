package ru.kapitoxa.mywallet.operationdetail

import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import ru.kapitoxa.mywallet.DummyViewModel
import ru.kapitoxa.mywallet.database.Operation
import ru.kapitoxa.mywallet.database.WalletDatabaseDao

@RunWith(RobolectricTestRunner::class)
class OperationDetailViewModelFactoryTest {

    private lateinit var viewModelFactory: OperationDetailViewModelFactory

    @Mock
    private lateinit var operation: Operation

    @Mock
    private lateinit var database: WalletDatabaseDao

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        viewModelFactory = OperationDetailViewModelFactory(operation, database)
    }

    @Test
    fun createOperationDetailViewModel() {
        val viewModel = viewModelFactory.create(OperationDetailViewModel::class.java)
        Assert.assertNotNull(viewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun createWrongViewModel() {
        viewModelFactory.create(DummyViewModel::class.java)
    }
}