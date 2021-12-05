package com.webaddicted.hiltsession.viewmodel

import androidx.lifecycle.MutableLiveData
import com.webaddicted.hiltsession.data.model.UserModel
import com.webaddicted.hiltsession.data.model.common.CommonRespo
import com.webaddicted.hiltsession.data.model.home.AppVersionRespo
import com.webaddicted.hiltsession.data.model.home.UserInfoRespo
import com.webaddicted.hiltsession.data.model.img.SearchReq
import com.webaddicted.hiltsession.data.model.img.SearchRespo
import com.webaddicted.hiltsession.data.respo.HomeRepository
import com.webaddicted.hiltsession.utils.apiutils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: HomeRepository) :
    BaseViewModel() {
    var getImageRespo = MutableLiveData<ApiResponse<SearchRespo>>()
    var userInfoRespo = MutableLiveData<ApiResponse<CommonRespo<UserInfoRespo>>>()
    var appVersionRespo = MutableLiveData<ApiResponse<CommonRespo<AppVersionRespo>>>()

    fun setPrefUserInfo(respo: UserInfoRespo?) {
        val userModel = UserModel().apply {
            Email = respo?.Email
            Id = respo?.Id
            MobilePhone = respo?.MobilePhone
            Name = respo?.Name
            Username = respo?.Username
            address = respo?.address
        }
        return repo.setPrefUserInfo(userModel = userModel)
    }

    fun getPrefUserInfo(): UserModel {
        return repo.getPrefUserInfo()
    }

    fun clearPref() {
        repo.clearPrefData()
    }

    fun setUpdateNotifyDays(days: Long) {
        repo.setUpdateNotifyDays(days)
    }

    fun getUpdateNotifyDays(): Long? {
        return repo.getUpdateNotifyDays()
    }


    fun userInfoApi() {
        userInfoRespo = MutableLiveData<ApiResponse<CommonRespo<UserInfoRespo>>>()
        repo.userInfoApi(userInfoRespo)
    }

    fun getAppVersion() {
        appVersionRespo = MutableLiveData<ApiResponse<CommonRespo<AppVersionRespo>>>()
        repo.getAppVersion(appVersionRespo)
    }

    fun getImages(req: SearchReq) {
        getImageRespo = MutableLiveData<ApiResponse<SearchRespo>>()
        repo.getImages(req, getImageRespo)
    }

}