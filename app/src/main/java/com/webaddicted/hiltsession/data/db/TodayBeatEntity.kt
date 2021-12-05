package com.webaddicted.hiltsession.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.webaddicted.hiltsession.utils.constant.ApiConstant

@Entity(tableName = ApiConstant.TODAY_BEAT_TABLE)
data class TodayBeatEntity(
    @PrimaryKey(autoGenerate = true)
    val customId: Long = 0L,
    @SerializedName("badgeIcon")
    val badgeIcon: String?,
    @SerializedName("beatStatus")
    val beatStatus: Boolean?,
    @SerializedName("comment")
    val comment: String?,
    @SerializedName("creditDays")
    val creditDays: Int?,
    @SerializedName("dataSync")
    val dataSync: Boolean?,
    @SerializedName("dseCode")
    val dseCode: String?,
    @SerializedName("dseName")
    val dseName: String?,
    @SerializedName("fencingDistance")
    val fencingDistance: Double?,
    @SerializedName("isGeofencing")
    val isGeofencing: Boolean?,
    @SerializedName("kycStatus")
    val kycStatus: Boolean?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("locationStatus")
    val locationStatus: Boolean?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("mobile")
    val mobile: String?,
    @SerializedName("monthSales")
    val monthSales: Int?,
    @SerializedName("pendingAmt")
    val pendingAmt: Int?,
    @SerializedName("pendingBillCount")
    val pendingBillCount: Int?,
    @SerializedName("pjpVisitId")
    val pjpVisitId: String?,
    @SerializedName("subDealerCode")
    val subDealerCode: String?,
    @SerializedName("subDealerId")
    val subDealerId: String?,
    @SerializedName("subDealerName")
    val subDealerName: String?,
    @SerializedName("whatsappNo")
    val whatsappNo: String?,
    @SerializedName("accountType")
    val accountType: String?,
    @SerializedName("inventoryLocation")
    val inventoryLocation: String?,
//    @Transient
    @SerializedName("lastStoreDate")
    val lastStoreDate: String?
)