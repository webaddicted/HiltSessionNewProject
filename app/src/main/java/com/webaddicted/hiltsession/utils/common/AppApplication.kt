package com.webaddicted.hiltsession.utils.common


import android.app.Application
import com.webaddicted.hiltsession.utils.sp.PreferenceUtils
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for our application.
 */
@HiltAndroidApp
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceUtils.getInstance(this)
//        if(BuildConfig.DEBUG)
//        Stetho.initializeWithDefaults(this)
    }
}