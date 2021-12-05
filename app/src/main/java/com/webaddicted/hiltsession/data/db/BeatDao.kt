package com.webaddicted.hiltsession.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BeatDao{
    //    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertUsers(vararg users: User?)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: List<TodayBeatEntity>)

//    @Query("select * From student ORDER BY studentId ASC")
//    fun getBeatsList() : LiveData<MutableList<TodayBeatEntity>>

//    @Query("SELECT count(studentId) FROM items") // items is the table in the @Entity tag of ItemsYouAreStoringInDB.kt, id is a primary key which ensures each entry in DB is unique
//    suspend fun numberOfItemsInDB() : Int //

    @Query("select * From TodayBeats")
    fun getBeatsList() : List<TodayBeatEntity>


    @Query("DELETE FROM TodayBeats")
    fun cleatTable()
}
