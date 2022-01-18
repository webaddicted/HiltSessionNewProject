package com.webaddicted.hiltsession.viewmodel


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.webaddicted.hiltsession.data.db.AppDatabase
import com.webaddicted.hiltsession.data.respo.LoginRepository
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoginViewModelTest :TestCase(){
    private lateinit var loginViewModel: LoginViewModel

    @Before
    override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        val userDao = db.userInfoDao()
        loginViewModel = LoginViewModel(LoginRepository())
    }

    @Test
    fun getGetDbUserInfoRespoTest() {

    }

    @Test
    fun setGetDbUserInfoRespoTest() {
    }

    @Test
    fun getSetDbUserInfoRespoTest() {
    }

    @Test
    fun setSetDbUserInfoRespoTest() {
    }

    @Test
    fun setPrefUserInfoTest() {
    }

    @Test
    fun getPrefUserInfoTest() {
    }

    @Test
    fun getDbUserInfoApiTest() {
    }

    @Test
    fun setDbUserInfoApiTest() {
    }

    @Test
    fun clearSharePrefTest() {
    }
}