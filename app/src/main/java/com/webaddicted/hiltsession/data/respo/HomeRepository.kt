package com.webaddicted.hiltsession.data.respo

import androidx.lifecycle.MutableLiveData
import com.webaddicted.hiltsession.data.model.character.CharacterRespo
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

    fun getCharacterList(characterRespo: MutableLiveData<ApiResponse<CharacterRespo>>) {
        object : DataFetchCall<CharacterRespo>(characterRespo) {
            override fun createCallAsync(): Deferred<Response<CharacterRespo>> {
                return apiServices.getCharacterList()
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