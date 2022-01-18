package com.webaddicted.hiltsession.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AppDatabaseTest : TestCase() {
    private lateinit var userDao: UserInfoDao
    private lateinit var db: AppDatabase

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userDao = db.userInfoDao()
    }

    private fun addDataInDb() = runBlocking {
        val user = UserInfoEntity(
            customId = 2,
            name = "Deepak",
            email = "test@gmail.com",
            mobilePhone = "9024061407",
            address = "Rajasthan"
        )
        userDao.insertUser(mutableListOf(user))
        return@runBlocking user
    }

    @Test
    fun insertUserTest() = runBlocking {
        val user = addDataInDb()
        val userInfoEntity = userDao.getUserInfoList("test@gmail.com")
        Truth.assertThat(userInfoEntity.email.equals(user.email)).isTrue()
    }

    @Test
    fun getUserInfoListTest() = runBlocking {
        val user = addDataInDb()
        val userInfoEntity = userDao.getUserInfoList(user.email)
        Truth.assertThat(userInfoEntity.email.equals(user.email)).isTrue()
    }

    @Test
    fun getAllUserInfoListTestTest() = runBlocking {
        addDataInDb()
        val userInfoEntity = userDao.getAllUserInfoList()
        Truth.assertThat(userInfoEntity.size == 1).isTrue()
    }

    @Test
    fun userCountTest() {
        addDataInDb()
        val userInfoEntity = userDao.getAllUserInfoList()
        Truth.assertThat(userInfoEntity.size == 1).isTrue()
    }

    @Test
    fun cleatTableTest() {
        addDataInDb()
        userDao.cleatTable()
        val userInfoEntity = userDao.getAllUserInfoList()
        Truth.assertThat(userInfoEntity.isEmpty()).isTrue()
    }

    @After
    fun closeDb() = db.close()
}