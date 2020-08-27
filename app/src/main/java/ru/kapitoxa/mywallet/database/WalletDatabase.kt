package ru.kapitoxa.mywallet.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import ru.kapitoxa.mywallet.R
import java.util.concurrent.Executors

@Database(
        entities = [
            Category::class,
            CategoryType::class,
            Operation::class
        ],
        views = [
            OperationDetail::class
        ],
        version = 1
)
abstract class WalletDatabase : RoomDatabase() {

    abstract val walletDatabaseDao: WalletDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: WalletDatabase? = null

        fun getInstance(context: Context): WalletDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            WalletDatabase::class.java,
                            "wallet_database"
                    )
                            .addCallback(Helper.onCreate(context))
                            .fallbackToDestructiveMigration()
                            .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }

    class Helper {
        companion object {
            fun onCreate(context: Context) = object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    INSTANCE?.let {
                        val scope = CoroutineScope(Executors.newSingleThreadScheduledExecutor()
                                .asCoroutineDispatcher())
                        scope.launch {
                            populate(context, it.walletDatabaseDao)
                        }
                    }
                }
            }

            suspend fun populate(context: Context, database: WalletDatabaseDao) {
                database.insertAllCategoryTypes(
                        CategoryType(name = context.getString(R.string.category_type_income)),
                        CategoryType(name = context.getString(R.string.category_type_expense))
                )
            }
        }
    }
}