package com.webaddicted.hiltsession.data.model.common

import com.google.gson.annotations.SerializedName


data class CommonWithoutMapReq(
    @SerializedName("queryParam")
    val queryParam: String?
)