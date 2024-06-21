package com.example.myapplication.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("select * from userlogin")
    fun getlogin(): List<LoginDB>
    @Query("delete from userlogin")
    fun dellogin()
    @Insert
    fun insertlogin(user:LoginDB)
}