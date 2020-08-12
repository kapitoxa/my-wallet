package ru.kapitoxa.mywallet.suite

import org.junit.runner.RunWith
import org.junit.runners.Suite
import ru.kapitoxa.mywallet.categorydetail.CategoryDetailViewModelFactoryTest
import ru.kapitoxa.mywallet.categorydetail.CategoryDetailViewModelTest

@RunWith(Suite::class)
@Suite.SuiteClasses(
        CategoryDetailViewModelTest::class,
        CategoryDetailViewModelFactoryTest::class
)
class UnitTestSuite