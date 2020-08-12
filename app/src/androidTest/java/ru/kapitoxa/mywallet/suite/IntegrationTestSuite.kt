package ru.kapitoxa.mywallet.suite

import org.junit.runner.RunWith
import org.junit.runners.Suite
import ru.kapitoxa.mywallet.database.WalletDatabaseTest

@RunWith(Suite::class)
@Suite.SuiteClasses(
        WalletDatabaseTest::class
)
class IntegrationTestSuite