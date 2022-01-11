package com.webaddicted.hiltsession.utils.sp

import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
//import com.google.common.truth.Truth.assertThat

@RunWith(JUnit4::class)
class PreferenceMgrTest {

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