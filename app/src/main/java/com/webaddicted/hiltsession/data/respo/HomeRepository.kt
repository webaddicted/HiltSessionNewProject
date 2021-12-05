package com.webaddicted.hiltsession.data.respo

import androidx.lifecycle.MutableLiveData
import com.webaddicted.hiltsession.data.model.common.CommonRespo
import com.webaddicted.hiltsession.data.model.home.AppVersionRespo
import com.webaddicted.hiltsession.data.model.home.UserInfoRespo
import com.webaddicted.hiltsession.data.model.img.SearchReq
import com.webaddicted.hiltsession.data.model.img.SearchRespo
import com.webaddicted.hiltsession.utils.apiutils.ApiResponse
import com.webaddicted.hiltsession.utils.apiutils.DataFetchCall
import com.webaddicted.hiltsession.utils.constant.ApiConstant
import kotlinx.coroutines.Deferred
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor() : BaseRepository() {
    fun getImages(
        strUrl: SearchReq,
        userInfoRepo: MutableLiveData<ApiResponse<SearchRespo>>
    ) {
        object : DataFetchCall<SearchRespo>(userInfoRepo) {
            override fun createCallAsync(): Deferred<Response<SearchRespo>> {
                return apiServices.searchApi(
                    ApiConstant.SERVER_KEY,
                    strUrl.text,
                    strUrl.page.toString(),
                    80.toString(),
                    "json",
                    "1"
                )
            }

            override fun shouldFetchFromDB(): Boolean {
                return false
            }

            override fun internetConnection(): Boolean {
                return networkHelper.isNetworkConnected()
            }
        }.execute()
    }


    fun userInfoApi(userInfoRepo: MutableLiveData<ApiResponse<CommonRespo<UserInfoRespo>>>) {
        object : DataFetchCall<CommonRespo<UserInfoRespo>>(userInfoRepo) {
            override fun createCallAsync(): Deferred<Response<CommonRespo<UserInfoRespo>>> {
                return apiServices.getUserInfo()
            }

            override fun shouldFetchFromDB(): Boolean {
                return false
            }

            override fun internetConnection(): Boolean {
                return networkHelper.isNetworkConnected()
            }
        }.execute()
    }

    fun getAppVersion(userInfoRepo: MutableLiveData<ApiResponse<CommonRespo<AppVersionRespo>>>) {
        object : DataFetchCall<CommonRespo<AppVersionRespo>>(userInfoRepo) {
            override fun createCallAsync(): Deferred<Response<CommonRespo<AppVersionRespo>>> {
                return apiServices.getAppVersion()
            }

            override fun shouldFetchFromDB(): Boolean {
                return false
            }

            override fun internetConnection(): Boolean {
                return networkHelper.isNetworkConnected()
            }
        }.execute()
    }

    fun setUpdateNotifyDays(days: Long) {
        spManager.setUpdateNotifyDays(days)
    }

    fun getUpdateNotifyDays(): Long? {
        return spManager.getUpdateNotifyDays()
    }
}