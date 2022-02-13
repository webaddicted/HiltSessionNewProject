package com.webaddicted.hiltsession.utils.apiutils

import com.webaddicted.hiltsession.data.model.character.CharacterRespo
import com.webaddicted.hiltsession.data.model.common.CommonRespo
import com.webaddicted.hiltsession.data.model.home.UserInfoRespo
import com.webaddicted.hiltsession.data.model.img.SearchRespo
import com.webaddicted.hiltsession.utils.constant.ApiConstant
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("services/apexrest/userInfoDMS")
    fun getUserInfo(): Deferred<Response<CommonRespo<UserInfoRespo>>>

    @GET("rest/?method=flickr.photos.search")
    fun searchApi(
        @Query("api_key") api_key: String,
        @Query("text") text: String,
        @Query("page") page: String,
        @Query("per_page") per_page: String,
        @Query("format") format: String,
        @Query("nojsoncallback") nojsoncallback: String
    ): Deferred<Response<SearchRespo>>

    @GET(ApiConstant.CHARACTER_API)
    fun getCharacterList(): Deferred<Response<CharacterRespo>>

}


