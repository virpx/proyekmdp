package com.example.myapplication.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username") var username:String,
    @ColumnInfo(name = "email") var email:String,
    @ColumnInfo(name = "fullname") var fullname:String,
    @ColumnInfo(name = "password") var password:String,
    @ColumnInfo(name = "gender") var gender:String,
    @ColumnInfo(name = "specialist") var specialist:String,
    @ColumnInfo(name = "sekolah") var sekolah:String,
    @ColumnInfo(name = "tahun_lulus") var tahun_lulus:String,
    @ColumnInfo(name = "lama_praktik") var lama_praktik:Int,
    @ColumnInfo(name = "foto_profile") var foto_profile: String = ""
)
