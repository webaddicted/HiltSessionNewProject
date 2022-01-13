package com.webaddicted.hiltsession.utils.sp

import com.webaddicted.hiltsession.data.model.home.UserInfoRespo
import com.webaddicted.hiltsession.utils.common.GlobalUtils
import com.webaddicted.hiltsession.utils.constant.PreferenceConstant
import javax.inject.Inject

/**
 * Author : Deepak Sharma(Webaddicted)
 * Email : techtamper@gmail.com
 * Profile : https://github.com/webaddicted
 */
class PreferenceMgr @Inject constructor(var preferenceUtils: PreferenceUtils) {
    /**
     * set user session info
     */
    fun setUserInfo(userModel: UserInfoRespo) {
        preferenceUtils.setPreference(
            PreferenceConstant.PREF_USER_MODEL,
            GlobalUtils.serializeObj(userModel)
        )
    }

    /**
     * get user session info
     */
    fun getUserInfo(): UserInfoRespo? {
        val userModel = preferenceUtils.getPreference(PreferenceConstant.PREF_USER_MODEL, "")
        return if (userModel != null) (GlobalUtils.deserializeObj(
            userModel,
            UserInfoRespo::class.java
        ) as UserInfoRespo)
        else null
    }

    fun setUpdateNotifyDays(days: Long) {
        preferenceUtils.setPreference(
            PreferenceConstant.KEY_UPDT_NOTFY_DT,
            days
        )
    }

    fun getUpdateNotifyDays(): Long? {
        return preferenceUtils.getPreference(PreferenceConstant.KEY_UPDT_NOTFY_DT, 0L)
    }

    fun removeKey(removeKey: String) {
        try {
            preferenceUtils.removeKey(removeKey)
        } catch (e: Exception) {
            GlobalUtils.logPrint(msg = e.toString())
        }
    }

    fun clearPref() {
        preferenceUtils.clearAllPreferences()
    }

    fun setOrderInTime(subDealerId: String?) {
        subDealerId?.let { preferenceUtils.setPreference(it, GlobalUtils.getCurrentTime()) }
    }

    fun getOrderInTime(subDealerId: String?): String? {
        return subDealerId?.let { preferenceUtils.getPreference(it, "") }
    }

}