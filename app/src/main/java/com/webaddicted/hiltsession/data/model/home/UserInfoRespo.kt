package com.webaddicted.hiltsession.data.model.home
import com.google.gson.annotations.SerializedName

data class UserInfoRespo(
    @SerializedName("Email")val Email: String?,
    @SerializedName("Id")val Id: String?,
    @SerializedName("MobilePhone")val MobilePhone: String?,
    @SerializedName("Name")val Name: String?,
    @SerializedName("Username")val Username: String?,
    @SerializedName("address")val address: String?
)