package com.webaddicted.hiltsession.data.model
import com.google.gson.annotations.SerializedName

class UserModel {
    @SerializedName("Email")
    var Email: String?=""
    @SerializedName("Id")var Id: String?=""
    @SerializedName("MobilePhone")var MobilePhone: String?=""
    @SerializedName("Name")var Name: String?=""
    @SerializedName("Username")var Username: String?=""
    @SerializedName("address")var address: String?=""
}