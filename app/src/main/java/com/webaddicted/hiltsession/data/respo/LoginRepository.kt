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

class LoginRepository @Inject constructor() : BaseRepository() {

    fun getDbUserInfoApi(
        emailId: String,
        dbUserInfoRespo: MutableLiveData<ApiResponse<UserInfoRespo>>
    ) {
        CoroutineScope(Dispatchers.IO).async {
            val respo = userInfoDao.getUserInfoList(emailId)
            dbUserInfoRespo.postValue(
                ApiResponse.success(
                    DBConverter.userInfoUiTypeRespo(
                        mutableListOf(
                            respo
                        ) as ArrayList<UserInfoEntity>
                    )[0]
                )
            )
        }
    }

    fun setDbUserInfoApi(
        userInfo: UserInfoRespo,
        dbUserInfoRespo: MutableLiveData<ApiResponse<String>>
    ) {
        CoroutineScope(Dispatchers.IO).async {
            try {
                userInfoDao.insertUser(DBConverter.userInfoToDbTypeRespo(mutableListOf(userInfo) as ArrayList<UserInfoRespo>))
                dbUserInfoRespo.postValue(ApiResponse.success(""))
            } catch (e: Exception) {
                Log.e("TAG", "Excep : $e")
                dbUserInfoRespo.postValue(ApiResponse.error(e))
            }

        }
    }
}