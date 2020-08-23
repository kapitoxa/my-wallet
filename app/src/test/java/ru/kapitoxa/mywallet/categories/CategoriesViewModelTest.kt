package ru.kapitoxa.mywallet.categories

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
import ru.kapitoxa.mywallet.database.Category
import ru.kapitoxa.mywallet.database.CategoryType
import ru.kapitoxa.mywallet.database.CategoryWithType
import ru.kapitoxa.mywallet.database.WalletDatabaseDao

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CategoriesViewModelTest {
    private lateinit var viewModel: CategoriesViewModel

    private lateinit var categoriesLiveData: LiveData<List<CategoryWithType>>

    private lateinit var navigateToCategoryDetailLiveData: LiveData<Category?>

    private val categoriesWithTypeLiveData: MutableLiveData<List<CategoryWithType>> = MutableLiveData()

    private val categoriesWithTypeList = listOf(
            CategoryWithType(
                    category = Category(1L, "Food", 2L),
                    type = CategoryType(2L, "Expense")
            ),
            CategoryWithType(
                    category = Category(2L, "Rent", 2L),
                    type = CategoryType(2L, "Expense")
            )
    )

    @Mock
    private lateinit var database: WalletDatabaseDao

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        categoriesWithTypeLiveData.value = categoriesWithTypeList
        `when`(database.getAllCategories()).thenReturn(categoriesWithTypeLiveData)

        viewModel = CategoriesViewModel(database)
        categoriesLiveData = viewModel.categories
        navigateToCategoryDetailLiveData = viewModel.navigateToCategoryDetail
    }

    @Test
    fun fillListOnInitialization() {
        Assert.assertEquals(categoriesWithTypeList, categoriesLiveData.value)
    }

    @Test
    fun fabClicked() {
        Assert.assertNull(navigateToCategoryDetailLiveData.value)

        viewModel.onFabClicked()

        val expected = Category()
        val actual = navigateToCategoryDetailLiveData.value

        Assert.assertEquals(expected, actual)

        viewModel.onNavigatedToCategoryDetail()

        Assert.assertNull(navigateToCategoryDetailLiveData.value)
    }

    @Test
    fun categoryClicked() {
        Assert.assertNull(navigateToCategoryDetailLiveData.value)

        val clickedCategory = categoriesWithTypeList[0].category

        viewModel.onCategoryClicked(clickedCategory)

        Assert.assertEquals(clickedCategory, navigateToCategoryDetailLiveData.value)

        viewModel.onNavigatedToCategoryDetail()

        Assert.assertNull(navigateToCategoryDetailLiveData.value)
    }
}