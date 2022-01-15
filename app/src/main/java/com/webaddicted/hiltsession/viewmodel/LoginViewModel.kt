package com.webaddicted.hiltsession.viewmodel

import androidx.lifecycle.MutableLiveData
import com.webaddicted.hiltsession.data.model.home.UserInfoRespo
import com.webaddicted.hiltsession.data.model.img.SearchReq
import com.webaddicted.hiltsession.data.model.img.SearchRespo
import com.webaddicted.hiltsession.data.respo.HomeRepository
import com.webaddicted.hiltsession.data.respo.LoginRepository
import com.webaddicted.hiltsession.utils.apiutils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: LoginRepository) :
    BaseViewModel() {
    var getDbUserInfoRespo = MutableLiveData<ApiResponse<UserInfoRespo>>()
    var setDbUserInfoRespo = MutableLiveData<ApiResponse<String>>()

    fun setPrefUserInfo(respo: UserInfoRespo?) {
        val userModel = UserInfoRespo(
            name = respo?.name,
            email = respo?.email,
            mobilePhone = respo?.mobilePhone,
            address = respo?.address
        )
        return repo.setPrefUserInfo(userModel = userModel)
    }

    fun getPrefUserInfo(): UserInfoRespo? {
        return repo.getPrefUserInfo()
    }

    fun getDbUserInfoApi(emailId: String) {
        getDbUserInfoRespo = MutableLiveData<ApiResponse<UserInfoRespo>>()
        repo.getDbUserInfoApi(emailId, getDbUserInfoRespo)
    }

    fun setDbUserInfoApi(userInfo: UserInfoRespo) {
        setDbUserInfoRespo = MutableLiveData<ApiResponse<String>>()
        repo.setDbUserInfoApi(userInfo, setDbUserInfoRespo)
    }
}