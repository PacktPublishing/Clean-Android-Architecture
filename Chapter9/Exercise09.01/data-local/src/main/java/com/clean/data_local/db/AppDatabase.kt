package com.clean.data_local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.clean.data_local.db.post.PostDao
import com.clean.data_local.db.post.PostEntity
import com.clean.data_local.db.user.UserDao
import com.clean.data_local.db.user.UserEntity

@Database(entities = [UserEntity::class, PostEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun postDao(): PostDao
}