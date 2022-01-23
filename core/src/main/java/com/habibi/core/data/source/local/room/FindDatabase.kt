package com.habibi.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.habibi.core.data.source.local.entity.UsersEntity

@Database(
    entities = [UsersEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FindDatabase: RoomDatabase() {
    abstract fun findDao(): FindDao
}