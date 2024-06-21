package com.example.myapplication.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userlogin")
data class LoginDB (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username") val username:String,
    @ColumnInfo(name = "password") var password:String,
    ){}