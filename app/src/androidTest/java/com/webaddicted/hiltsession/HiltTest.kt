package com.webaddicted.hiltsession

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Author : Deepak Sharma(webaddicted)
 * Email : techtamper@gmail.com
 * Profile : https://github.com/webaddicted
 */
@HiltAndroidTest
class HiltTest {
    @get:Rule(order = 1)
    val rule = HiltAndroidRule(this)

    @Before
    fun init() {
        rule.inject()
    }

    @Test
    fun testCode() {
//        rule.inject()
    }
}