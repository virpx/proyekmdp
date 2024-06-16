package com.example.myapplication.Database

import com.example.myapplication.Admin_class_list_user

class DefaultRepository(private val localDataSource:AppDatabase, private val remoteDataSource:MdpService){

    suspend fun getAllUser(force: Boolean = false): List<User> {
        return remoteDataSource.getAllUsers()
    }

    suspend fun getUserByUsername(username: String): User {
        return remoteDataSource.getUserByUsername(username)
    }

    suspend fun createUser(user: User) {
        val newData = remoteDataSource.createUser(user)
    }

    suspend fun updateUser(user: User) {
        val updateData = remoteDataSource.updateUser(user.username, user)
    }

    suspend fun deleteUser(user: User) {
        val deleteData = remoteDataSource.deleteUser(user.username)
    }

    suspend fun loginUser(loginRequest: Map<String, String>): Map<String, String>{
        return remoteDataSource.loginUser(loginRequest)
    }
    suspend fun admingetluser(): Admin_class_list_user {
        return remoteDataSource.admingetluser()
    }
}