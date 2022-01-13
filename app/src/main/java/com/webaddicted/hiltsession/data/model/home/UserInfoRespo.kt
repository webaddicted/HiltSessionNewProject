package com.webaddicted.hiltsession.data.model.home
import com.google.gson.annotations.SerializedName

data class UserInfoRespo(
    @SerializedName("name")val name: String?,
    @SerializedName("email")val email: String?,
    @SerializedName("mobilePhone")val mobilePhone: String?,
    @SerializedName("address")val address: String?
)