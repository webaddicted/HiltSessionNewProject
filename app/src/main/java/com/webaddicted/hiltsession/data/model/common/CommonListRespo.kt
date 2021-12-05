package com.webaddicted.hiltsession.data.model.common
import com.google.gson.annotations.SerializedName

import java.io.Serializable

data class CommonListRespo<T>(
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("respDetails")
    val respDetails: ArrayList<T>?,
    @SerializedName("strMessage")
    val strMessage: String?
) : Serializable