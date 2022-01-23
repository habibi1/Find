package com.habibi.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_entities")
data class UsersEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "type")
    val type: String?,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?
)
