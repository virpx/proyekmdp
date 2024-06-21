package com.example.myapplication.Database

import com.example.myapplication.Admin.Admin_class_dashboard
import com.example.myapplication.Admin.Admin_class_list_artikel
import com.example.myapplication.Admin.Admin_class_list_regis_dokter
import com.example.myapplication.Admin.Admin_class_list_user
import com.example.myapplication.Admin.Admin_class_review_user
import com.example.myapplication.Bodyaddfoodtrack
import com.example.myapplication.ChatBody
import com.example.myapplication.Classuniversal_bubble
import com.example.myapplication.Classuniversal_chat
import com.example.myapplication.Classuniversal_foodtrack
import com.example.myapplication.Classuniversal_hasilgizi
import com.example.myapplication.Doctor.Artikel
import com.example.myapplication.Doctor.Review
import com.example.myapplication.KirimOtp
import com.example.myapplication.Recipesend
import com.example.myapplication.User.HChat
import com.example.myapplication.User.Resep
import com.example.myapplication.changePw
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

    @GET("admin/lartikel")
    suspend fun admingetlartikel(): MutableList<Admin_class_list_artikel>

    @GET("admin/ldokterregis")
    suspend fun admingetregisdokter(): MutableList<Admin_class_list_regis_dokter>

    @DELETE("admin/hapusdokterregis/{username}")
    suspend fun admindeletedokterregis(@Path("username") username: String)

    @GET("admin/accdokterregis/{username}")
    suspend fun adminaccdokterregis(@Path("username") username: String)

    @DELETE("admin/hapusartikel/{id}")
    suspend fun adminhapusartkel(@Path("id") id: Int)

    @DELETE("admin/hapususer/{username}")
    suspend fun adminhapususer(@Path("username") username: String)

    @GET("admin/homedashboard")
    suspend fun admingetdashboard(): Admin_class_dashboard

    @GET("getlistchat/{username}")
    suspend fun usergetlistchat(@Path("username") username: String): MutableList<Classuniversal_chat>

    @GET("getisichat/{idhchat}")
    suspend fun usergetbubble(@Path("idhchat") idhchat: Int): MutableList<Classuniversal_bubble>

    @GET("user/artikel")
    suspend fun usergetartikel(): MutableList<Artikel>

    @GET("user/doctor")
    suspend fun getdokters(): MutableList<User>

    @POST("/dokter/uploadartikel")
    suspend fun uploadArtikel(@Body artikel: Artikel): Artikel

    @POST("user/addchatbiasa/{idhchat}/{pengirim}/{penerima}")
    suspend fun sendpesanbiasa(
        @Path("idhchat") idhchat: Int,
        @Path("pengirim") pengirim: String,
        @Path("penerima") penerima: String,
        @Body isi: ChatBody
    )

    @GET("user/getfoodtrack/{username}")
    suspend fun getlistfoodtrack(@Path("username") username: String): MutableList<Classuniversal_foodtrack>

    @PUT("dokter/{username}")
    suspend fun updateDokterProfile(@Path("username") username: String, @Body user: User): User

    @POST("sendotp")
    suspend fun sendotp(@Body dataotp: KirimOtp): KirimOtp

    @PUT("changepassword/{email}")
    suspend fun changePassword(@Path("email") email: String, @Body password: changePw)

    @PUT("user/{username}")
    suspend fun updateUserProfile(@Path("username") username: String, @Body user: User): User

    @PUT("updateViewArtikel")
    suspend fun updateViewArtikel(@Body artikel: Artikel): Artikel

    @GET("dokter/historyreview/{username_target}")
    suspend fun gethistoryreview(@Path("username_target") username_target: String): MutableList<Review>

    @GET("/average-rating/{username_target}")
    suspend fun getAverageRating(@Path("username_target") usernameTarget: String): Map<String, String>

    @PUT("changepassword/{email}")
    suspend fun changePassword(@Path("email") email: String, @Body password: String)

    @POST("user/addchatfoodtrack/{idhchat}/{pengirim}/{penerima}")
    suspend fun sendpesanfoodtrack(
        @Path("idhchat") idhchat: Int,
        @Path("pengirim") pengirim: String,
        @Path("penerima") penerima: String,
        @Body isi: ChatBody
    )

    @GET("user/searchfood")
    suspend fun getgizidata(@Query("cari") cari: String): Classuniversal_hasilgizi

    @POST("user/addfoodtrack/{username}")
    suspend fun tambahfoodtrack(
        @Path("username") username: String,
        @Body isi: Bodyaddfoodtrack
    )

    @POST("registerdokter")
    suspend fun createDokter(@Body user: User): User

    @GET("dokter/reviewuser/{idhcat}/{usernamelawan}/{username}")
    suspend fun endchatdokter(@Path("idhcat") idhcat:Int,@Path("usernamelawan") usernamelawan:String,@Path("username") username:String,@Query("isi") isi: String,@Query("rating") rating: Float,@Query("kesimpulan") kesimpulan: String)
    @POST("dokter/addrecipe/{idhcat}")
    suspend fun dokteraddresep(@Path("idhcat") idhcat:Int,@Body isi: List<Recipesend>)
    suspend fun endchatdokter(
        @Path("idhcat") idhcat: Int,
        @Path("usernamelawan") usernamelawan: String,
        @Path("username") username: String,
        @Query("isi") isi: String,
        @Query("rating") rating: Float,
        @Query("kesimpulan") kesimpulan: String
    )

    @GET("user/h_chat/{user1}")
    suspend fun getHchat(@Path("user1") username: String): MutableList<HChat>

    @GET("user/resep/{user2}/{kesimpulan}")
    suspend fun getResep(@Path("user2") username: String, @Path("kesimpulan") kesimpulan: String)
            : MutableList<Resep>
}
