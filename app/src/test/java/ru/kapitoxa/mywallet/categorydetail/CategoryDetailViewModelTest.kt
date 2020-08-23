package ru.kapitoxa.mywallet.categorydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import ru.kapitoxa.mywallet.MainCoroutineRule
import ru.kapitoxa.mywallet.database.Category
import ru.kapitoxa.mywallet.database.CategoryType
import ru.kapitoxa.mywallet.database.WalletDatabaseDao

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CategoryDetailViewModelTest {
    private lateinit var viewModel: CategoryDetailViewModel

    private lateinit var categoryLiveData: LiveData<Category>

    private lateinit var typesLiveData: LiveData<List<CategoryType>>

    private lateinit var showCategoryNameFieldErrorLiveData: LiveData<Boolean>

    private lateinit var navigateToCategoriesLiveData: LiveData<Boolean>

    private val types = listOf(
            CategoryType(1, "Income"),
            CategoryType(2, "Expense")
    )

    private val categoryTypeListLiveData: MutableLiveData<List<CategoryType>> = MutableLiveData()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var database: WalletDatabaseDao

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        categoryTypeListLiveData.value = types

        `when`(database.getAllCategoryTypes()).thenReturn(categoryTypeListLiveData)
        viewModel = CategoryDetailViewModel(Category(), database, mainCoroutineRule.testDispatcher)

        categoryLiveData = viewModel.category
        typesLiveData = viewModel.types
        showCategoryNameFieldErrorLiveData = viewModel.showCategoryNameFieldError
        navigateToCategoriesLiveData = viewModel.navigateToCategories
    }

    @Test
    fun fillTypesOnInitialization() {
        Assert.assertEquals(types, typesLiveData.value)
    }

    @Test
    fun checkType() {
        val category = categoryLiveData.value!!

        viewModel.onTypeChanged(1, true)

        Assert.assertEquals(1, category.typeId)
    }

    @Test
    fun changeType() {
        val category = categoryLiveData.value!!

        viewModel.onTypeChanged(1, true)
        viewModel.onTypeChanged(2, true)
        viewModel.onTypeChanged(1, false)

        Assert.assertEquals(2, category.typeId)
    }

    @Test
    fun showNameFieldError() {
        viewModel.onSave()
        Assert.assertTrue(showCategoryNameFieldErrorLiveData.value!!)
    }

    @Test
    fun navigateAfterSave() {
        val category = categoryLiveData.value!!
        category.name = "Some name"
        category.typeId = 1

        viewModel.onSave()
        Assert.assertTrue(navigateToCategoriesLiveData.value!!)

        viewModel.onNavigatedToCategories()
        Assert.assertFalse(navigateToCategoriesLiveData.value!!)
    }
}