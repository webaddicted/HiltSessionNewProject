package com.webaddicted.hiltsession.data.db

import com.webaddicted.hiltsession.data.model.home.UserInfoRespo

object DBConverter {
    fun userInfoUiTypeRespo(data: ArrayList<UserInfoEntity>): List<UserInfoRespo> {
        val userInfoRespo: ArrayList<UserInfoRespo> = arrayListOf()
        for (index in 0 until data.size) {
            val entity = data[index]
            userInfoRespo.add(
                UserInfoRespo(
                    entity.name,
                    entity.email,
                    entity.mobilePhone,
                    entity.address,
                )
            )
        }
        return userInfoRespo
    }

    fun userInfoToDbTypeRespo(userInfoRespo: ArrayList<UserInfoRespo>): ArrayList<UserInfoEntity> {
        val userInfoEntity: ArrayList<UserInfoEntity> = arrayListOf()
        for (index in 0 until userInfoRespo.size) {
            val entity = userInfoRespo[index]
            userInfoEntity.add(
                UserInfoEntity(
                    (index + 1).toLong(),
                    entity.name,
                    entity.email,
                    entity.mobilePhone,
                    entity.address,
                )
            )
        }
        return userInfoEntity
    }


}
