package com.webaddicted.hiltsession.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserInfoDao {
    //    @Query("select * From student ORDER BY studentId ASC")
//    fun getBeatsList() : LiveData<MutableList<TodayBeatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(student: List<UserInfoEntity>)

    @Query("select * From UserInfo WHERE email >= :emailId")
    suspend fun getUserInfoList(emailId: String?): UserInfoEntity

    @Query("select * From UserInfo ORDER BY name ASC")
    fun getAllUserInfoList(): List<UserInfoEntity>

    @Query("SELECT count(email) FROM UserInfo") // items is the table in the @Entity tag of ItemsYouAreStoringInDB.kt, id is a primary key which ensures each entry in DB is unique
    suspend fun userCount(): Int //

    @Query("DELETE FROM UserInfo")
    fun cleatTable()
}
