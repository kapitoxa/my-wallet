package ru.kapitoxa.mywallet.categorydetail

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
import ru.kapitoxa.mywallet.database.Category
import ru.kapitoxa.mywallet.database.WalletDatabaseDao

@RunWith(RobolectricTestRunner::class)
class CategoryDetailViewModelFactoryTest {

    @Mock
    private lateinit var database: WalletDatabaseDao

    @Mock
    private lateinit var category: Category

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var factory: CategoryDetailViewModelFactory

    @Before
    fun setUp() {
        factory = CategoryDetailViewModelFactory(category, database)
    }

    @Test
    fun createViewModel() {
        val viewModel = factory.create(CategoryDetailViewModel::class.java)
        Assert.assertNotNull(viewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun tryToCreateWrongViewModel() {
        factory.create(DummyViewModel::class.java)
    }
}