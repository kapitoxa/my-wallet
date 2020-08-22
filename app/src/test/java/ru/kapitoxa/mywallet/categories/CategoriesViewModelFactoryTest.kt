package ru.kapitoxa.mywallet.categories

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

@RunWith(RobolectricTestRunner::class)
class CategoriesViewModelFactoryTest {

    @Mock
    private lateinit var database: WalletDatabaseDao

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var factory: CategoriesViewModelFactory

    @Before
    fun setUp() {
        factory = CategoriesViewModelFactory(database)
    }

    @Test
    fun createCategoriesViewModel() {
        val viewModel = factory.create(CategoriesViewModel::class.java)
        Assert.assertNotNull(viewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun createUnsupportedViewModel() {
        factory.create(DummyViewModel::class.java)
    }
}