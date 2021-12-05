package com.webaddicted.hiltsession.data.model.common

import com.google.gson.annotations.SerializedName

data class CommonReq<T>(
    @SerializedName("filterMap")
    val filterMap: T?,
    @SerializedName("queryParam")
    val queryParam: String?
)