package com.example.myapplication.Database

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

    suspend fun updatePost(user: User) {
        val updateData = remoteDataSource.updateUser(user.username, user)
    }

    suspend fun deletePost(user: User) {
        val deleteData = remoteDataSource.deleteUser(user.username)
    }

}