package com.example.usersregistercompose.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserRoomDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}