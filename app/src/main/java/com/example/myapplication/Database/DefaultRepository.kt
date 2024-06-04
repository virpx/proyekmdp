package com.example.myapplication.Database

class DefaultRepository(private val dataSource:AppDatabase){

    suspend fun getAllUser(force: Boolean = false): List<User> {
        return dataSource.UserDao().getAll()
    }

    suspend fun getUserByUsername(username: String): User {
        return dataSource.UserDao().getByUsername(username)
    }

    suspend fun createUser(user: User) {
        val posts = dataSource.UserDao().getAll()
        dataSource.UserDao().insert(user)
    }

    suspend fun updatePost(user: User) {
        dataSource.UserDao().update(user)
    }

    suspend fun deletePost(user: User) {
        dataSource.UserDao().delete(user)
    }

}