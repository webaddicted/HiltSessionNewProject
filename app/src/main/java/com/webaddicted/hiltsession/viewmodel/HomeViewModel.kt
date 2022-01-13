package com.webaddicted.hiltsession.viewmodel

import androidx.lifecycle.MutableLiveData
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
    var getDbUserInfoRespo = MutableLiveData<UserInfoRespo>()
    var setDbUserInfoRespo = MutableLiveData<UserInfoRespo>()
    var getImageRespo = MutableLiveData<ApiResponse<SearchRespo>>()

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

    fun getDbUserInfoApi() {
        getDbUserInfoRespo = MutableLiveData<UserInfoRespo>()
        repo.getDbUserInfoApi(getDbUserInfoRespo)
    }

    fun setDbUserInfoApi() {
        setDbUserInfoRespo = MutableLiveData<UserInfoRespo>()
        repo.setDbUserInfoApi(setDbUserInfoRespo)
    }

    fun getImages(req: SearchReq) {
        getImageRespo = MutableLiveData<ApiResponse<SearchRespo>>()
        repo.getImages(req, getImageRespo)
    }

}