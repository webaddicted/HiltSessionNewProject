package com.webaddicted.hiltsession.data.db

import com.webaddicted.hiltsession.data.model.common.CommonListRespo
import com.webaddicted.hiltsession.data.model.home.BeatRespo
import com.webaddicted.hiltsession.utils.apiutils.ApiResponse
import com.webaddicted.hiltsession.utils.common.GlobalUtils

object DBConverter {
    fun beatToUiTypeRespo(data: ArrayList<TodayBeatEntity>): ApiResponse<CommonListRespo<BeatRespo>> {
        val beatRespo: ArrayList<BeatRespo> = arrayListOf()
        for (index in 0 until data.size) {
            val entity = data[index]
            beatRespo.add(
                BeatRespo(
                    entity.badgeIcon,
                    entity.beatStatus,
                    entity.comment,
                    entity.creditDays,
                    entity.dataSync,
                    entity.dseCode,
                    entity.dseName,
                    entity.fencingDistance,
                    entity.isGeofencing,
                    entity.kycStatus,
                    entity.latitude,
                    entity.locationStatus,
                    entity.longitude,
                    entity.mobile,
                    entity.monthSales,
                    entity.pendingAmt,
                    entity.pendingBillCount,
                    entity.pjpVisitId,
                    entity.subDealerCode,
                    entity.subDealerId,
                    entity.subDealerName,
                    entity.whatsappNo,
                    entity.accountType,
                    entity.inventoryLocation,
                )
            )
        }
        return ApiResponse.success(CommonListRespo(true, beatRespo,""))
    }

    fun beatToDbTypeRespo(todayBeatData: ArrayList<BeatRespo>): ArrayList<TodayBeatEntity> {
        val beatDBRespo: ArrayList<TodayBeatEntity> = arrayListOf()
        for (index in 0 until todayBeatData.size) {
            val entity = todayBeatData[index]
            beatDBRespo.add(
                TodayBeatEntity(
                    (index+1).toLong(),
                    entity.badgeIcon,
                    entity.beatStatus,
                    entity.comment,
                    entity.creditDays,
                    entity.dataSync,
                    entity.dseCode,
                    entity.dseName,
                    entity.fencingDistance,
                    entity.isGeofencing,
                    entity.kycStatus,
                    entity.latitude,
                    entity.locationStatus,
                    entity.longitude,
                    entity.mobile,
                    entity.monthSales,
                    entity.pendingAmt,
                    entity.pendingBillCount,
                    entity.pjpVisitId,
                    entity.subDealerCode,
                    entity.subDealerId,
                    entity.subDealerName,
                    entity.whatsappNo,
                    entity.accountType,
                    entity.inventoryLocation,
                    GlobalUtils.getCurrentDate()
                )
            )
        }
        return beatDBRespo
    }


}
