package com.habibi.core.utils

import com.habibi.core.data.source.local.entity.UsersEntity
import com.habibi.core.data.source.remote.response.UsersItem

object DataMapper {
    fun mapResponseToEntity(input: List<UsersItem?>): List<UsersEntity>{
        val result = ArrayList<UsersEntity>()

        input.forEach {
            result.add(
                UsersEntity(
                    it?.login.toString(),
                    it?.type,
                    it?.avatarUrl
                )
            )
        }

        return result
    }
}