package ru.kapitoxa.mywallet.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [
    Category::class,
    CategoryType::class,
    Operation::class
], version = 1)
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
                            .createFromAsset("database/wallet_database.db")
                            .fallbackToDestructiveMigration()
                            .build()
                }

                return instance
            }
        }
    }
}