package com.webaddicted.hiltsession.utils.sp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PreferenceMgrTest : TestCase() {

    @Before
    override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        PreferenceUtils.getInstance(context)
    }

    @Test
    fun setUpdateNotifyDays() {
        PreferenceMgr(PreferenceUtils()).setUpdateNotifyDays(125)
    }

    @Test
    fun getUpdateNotifyDays() {
        val result = PreferenceMgr(PreferenceUtils()).getUpdateNotifyDays()
        Truth.assertThat(result).isEqualTo(null)
    }
}