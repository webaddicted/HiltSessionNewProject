package com.webaddicted.hiltsession.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.webaddicted.hiltsession.utils.constant.ApiConstant

@Database(entities = [UserInfoEntity::class], version = ApiConstant.DB_VERSION,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao
}

