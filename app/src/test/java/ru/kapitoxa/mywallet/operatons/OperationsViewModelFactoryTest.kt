package ru.kapitoxa.mywallet.operatons

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
import ru.kapitoxa.mywallet.database.WalletDatabaseDao
import ru.kapitoxa.mywallet.operations.OperationsViewModel
import ru.kapitoxa.mywallet.operations.OperationsViewModelFactory

@RunWith(RobolectricTestRunner::class)
class OperationsViewModelFactoryTest {

    private lateinit var viewModelFactory: OperationsViewModelFactory

    @Mock
    private lateinit var database: WalletDatabaseDao

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        viewModelFactory = OperationsViewModelFactory(database)
    }

    @Test
    fun createOperationsViewModel() {
        val viewModel = viewModelFactory.create(OperationsViewModel::class.java)
        Assert.assertNotNull(viewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `create view model not registered in factory`() {
        viewModelFactory.create(DummyViewModel::class.java)
    }
}