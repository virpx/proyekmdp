package com.example.myapplication.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 2)
abstract class AppDatabase:RoomDatabase() {
    abstract fun UserDao(): UserDao
}