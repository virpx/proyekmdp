package com.example.myapplication.Database

import com.example.myapplication.Admin_class_list_user
import com.example.myapplication.Admin_class_review_user
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MdpService {
    @GET("users")
    suspend fun getAllUsers(@Query("q") q: String = ""): List<User>

    @GET("users/{username}")
    suspend fun getUserByUsername(@Path("username") username: String): User

    @POST("users")
    suspend fun createUser(@Body user: User): User

    @PUT("users/{username}")
    suspend fun updateUser(@Path("username") username: String, @Body user: User): User

    @DELETE("users/{username}")
    suspend fun deleteUser(@Path("username") username: String): User

    @POST("login")
    suspend fun loginUser(@Body loginRequest: Map<String, String>): Map<String, String>
    @GET("admin/getluser")
    suspend fun admingetluser(): MutableList<Admin_class_list_user>
    @GET("admin/lreview/{username}")
    suspend fun admingetuserreview(@Path("username") username: String): MutableList<Admin_class_review_user>
}
