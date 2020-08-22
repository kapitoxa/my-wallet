package ru.kapitoxa.mywallet.suite

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite
import ru.kapitoxa.mywallet.categories.CategoriesViewModelFactoryTest
import ru.kapitoxa.mywallet.categories.CategoriesViewModelTest
import ru.kapitoxa.mywallet.categorydetail.CategoryDetailViewModelFactoryTest
import ru.kapitoxa.mywallet.categorydetail.CategoryDetailViewModelTest

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
        CategoryDetailViewModelTest::class,
        CategoryDetailViewModelFactoryTest::class,
        CategoriesViewModelTest::class,
        CategoriesViewModelFactoryTest::class
)
class UnitTestSuite