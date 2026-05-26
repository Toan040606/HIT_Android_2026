package com.fragmentlayoutlogin.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fragmentlayoutlogin.core.database.dao.UserDao
import com.fragmentlayoutlogin.core.database.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}