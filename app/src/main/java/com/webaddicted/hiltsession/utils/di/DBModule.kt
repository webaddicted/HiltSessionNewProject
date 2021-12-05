package com.webaddicted.hiltsession.utils.di

import android.content.Context
import androidx.room.Room
import com.webaddicted.hiltsession.data.db.AppDatabase
import com.webaddicted.hiltsession.utils.constant.ApiConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {
    // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Singleton
    @Provides
    fun provideYourDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            ApiConstant.DB_NAME
        )
//            .addMigrations(MIGRATION_1_2)
            .build()
    // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideYourDao(db: AppDatabase) =
        db.todayBeatDao() // The reason we can implement a Dao for the database

//    private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL(
//                """
//                CREATE TABLE commitmentSheet (
//                    id TEXT PRIMARY KEY NOT NULL,
//                    commitmentType TEXT NOT NULL,
//                    commitmentDetails TEXT NOT NULL,
//                    targetDate TEXT NOT NULL,
//                    signImg TEXT NOT NULL,
//                    isImgSynced INTEGER NOT NULL
//                )
//                """.trimIndent()
//            )
//        }
//    }

//    private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            try {
//                val c = database.query("SELECT * FROM customer")
//                c.use {
//                    val cvList = ArrayList<ContentValues>()
//                    while (c.moveToNext()) {
//                        val cv = ContentValues()
//                        cv.put("id", c.getString(c.getColumnIndex("id")))
//                        cv.put("isSynced", c.getInt(c.getColumnIndex("isSynced")))
//                        cvList.add(cv)
//                    }
//                    database.execSQL("DROP TABLE IF EXISTS `customer`")
//                    createCustomerTable(database)
//                    for (row in cvList) {
//                        database.insert("customer", 0, row)
//                    }
//                }
//            } catch (e: SQLiteException) {
//                Log.e(e.message, "SQLiteException in migrate from database version 7 to version 8")
//            } catch (e: Exception) {
//                Log.e(e.message, "Failed to migrate database version 7 to version 8")
//            }
//        }
//
//        fun createCustomerTable(database: SupportSQLiteDatabase) {
//            database.execSQL(
//                """CREATE TABLE IF NOT EXISTS `customer` (
//                            `id` TEXT NOT NULL,
//                            `isSynced` INTEGER NOT NULL,
//                            `isActive` INTEGER NOT NULL,
//                            PRIMARY KEY(`id`))""".trimIndent()
//            )
//        }
//
//
//    }
}