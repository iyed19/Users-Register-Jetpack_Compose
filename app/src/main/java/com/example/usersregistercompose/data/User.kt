package com.example.usersregistercompose.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_data_table")
data class User(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo("user_id")
    val id: Int = 0,

    @ColumnInfo("user_name")
    val name: String,

    @ColumnInfo("user_email")
    val email: String
)