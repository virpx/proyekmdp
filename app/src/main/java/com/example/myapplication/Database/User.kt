package com.example.myapplication.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username") var username:String,
    @ColumnInfo(name = "email") var email:String,
    @ColumnInfo(name = "fullname") var fullname:String,
    @ColumnInfo(name = "password") var password:String,
    @ColumnInfo(name = "gender") var gender:String,
    @ColumnInfo(name = "specialist") var specialist:String,
)