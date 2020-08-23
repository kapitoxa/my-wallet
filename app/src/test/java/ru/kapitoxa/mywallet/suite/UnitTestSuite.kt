package ru.kapitoxa.mywallet.suite

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite
import ru.kapitoxa.mywallet.categories.CategoriesViewModelFactoryTest
import ru.kapitoxa.mywallet.categories.CategoriesViewModelTest
import ru.kapitoxa.mywallet.categorydetail.CategoryDetailViewModelFactoryTest
import ru.kapitoxa.mywallet.categorydetail.CategoryDetailViewModelTest
import ru.kapitoxa.mywallet.operationdetail.OperationDetailViewModelFactoryTest
import ru.kapitoxa.mywallet.operationdetail.OperationDetailViewModelTest

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
        CategoryDetailViewModelTest::class,
        CategoryDetailViewModelFactoryTest::class,
        CategoriesViewModelTest::class,
        CategoriesViewModelFactoryTest::class,
        OperationDetailViewModelTest::class,
        OperationDetailViewModelFactoryTest::class
)
class UnitTestSuite
