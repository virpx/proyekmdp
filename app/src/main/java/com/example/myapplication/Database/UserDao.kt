package com.example.myapplication.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("select * from users order by username desc")
    fun getAll(): List<User>

    @Query("select * from users where username=:username")
    fun getByUsername(username:String): User

    @Insert
    fun insert(post: User)

    @Update
    fun update(post: User)

    @Delete
    fun delete(post: User)

    @Query("delete from users")
    fun clearDb()

    @Insert
    fun insertMany(users:List<User>)
}