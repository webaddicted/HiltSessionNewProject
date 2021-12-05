package com.webaddicted.hiltsession.data.model.home
import com.google.gson.annotations.SerializedName

data class AppVersionRespo(
    @SerializedName("message")
    val message: String?,
    @SerializedName("minVersion")
    var minVersion: String?,
    @SerializedName("preferredVersion")
    var preferredVersion: String?
)