package com.webaddicted.hiltsession.data.respo

import com.webaddicted.hiltsession.data.db.UserInfoDao
import com.webaddicted.hiltsession.data.model.home.UserInfoRespo
import com.webaddicted.hiltsession.utils.apiutils.ApiServices
import com.webaddicted.hiltsession.utils.common.NetworkHelper
import com.webaddicted.hiltsession.utils.sp.PreferenceMgr
import javax.inject.Inject

open class BaseRepository @Inject constructor() {
    @Inject
    lateinit var apiServices: ApiServices

    @Inject
    lateinit var spManager: PreferenceMgr

    @Inject
    lateinit var networkHelper: NetworkHelper

    @Inject
    lateinit var userInfoDao: UserInfoDao

    fun getPrefUserInfo(): UserInfoRespo? {
        return spManager.getUserInfo()
    }

    fun setPrefUserInfo(userModel: UserInfoRespo) {
        spManager.setUserInfo(userModel = userModel)
    }
    fun clearPref() {
        spManager.clearPref()
    }
}
