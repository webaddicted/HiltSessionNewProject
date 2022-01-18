package com.webaddicted.hiltsession.viewmodel


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.webaddicted.hiltsession.data.db.AppDatabase
import com.webaddicted.hiltsession.data.respo.LoginRepository
import com.webaddicted.hiltsession.getOrAwaitValue
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoginViewModelTest : TestCase() {
    private lateinit var loginViewModel: LoginViewModel

    //    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Before
    override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        val userDao = db.userInfoDao()
        loginViewModel = LoginViewModel(LoginRepository())
    }

    @Test
    fun setPrefUserInfoTest() {
    }

    @Test
    fun getPrefUserInfoTest() {
    }

    @Test
    fun getDbUserInfoApiTest() {
        loginViewModel.getDbUserInfoApi("test@gmail.com")
        val result = loginViewModel.getDbUserInfoRespo.getOrAwaitValue().data
        assertThat(result != null).isTrue()
    }

    @Test
    fun setDbUserInfoApiTest() {
    }

    @Test
    fun clearSharePrefTest() {
    }
}