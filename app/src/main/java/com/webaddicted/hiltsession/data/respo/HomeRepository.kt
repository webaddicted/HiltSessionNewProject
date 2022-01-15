package com.webaddicted.hiltsession.data.respo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.webaddicted.hiltsession.data.db.DBConverter
import com.webaddicted.hiltsession.data.db.UserInfoEntity
import com.webaddicted.hiltsession.data.model.home.UserInfoRespo
import com.webaddicted.hiltsession.data.model.img.SearchReq
import com.webaddicted.hiltsession.data.model.img.SearchRespo
import com.webaddicted.hiltsession.utils.apiutils.ApiResponse
import com.webaddicted.hiltsession.utils.apiutils.DataFetchCall
import com.webaddicted.hiltsession.utils.constant.ApiConstant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
}