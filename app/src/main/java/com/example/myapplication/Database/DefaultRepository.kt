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
import com.example.myapplication.changePw

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
    suspend fun admingetluser(): MutableList<Admin_class_list_user>{
        return remoteDataSource.admingetluser()
    }
    suspend fun admingetuserreview(username:String): MutableList<Admin_class_review_user>{
        return remoteDataSource.admingetuserreview(username)
    }
    suspend fun admingetlartikel(): MutableList<Admin_class_list_artikel>{
        return remoteDataSource.admingetlartikel()
    }

    suspend fun admingetregisdokter(): MutableList<Admin_class_list_regis_dokter>{
        return remoteDataSource.admingetregisdokter()
    }
    suspend fun admindeletedokterregis(username: String){
        return remoteDataSource.admindeletedokterregis(username)
    }
    suspend fun adminaccdokterregis(username: String){
        return remoteDataSource.adminaccdokterregis(username)
    }
    suspend fun adminhapusartkel(id: Int){
        return remoteDataSource.adminhapusartkel(id)
    }
    suspend fun adminhapususer(username: String){
        return remoteDataSource.adminhapususer(username)
    }
    suspend fun admingetdashboard(): Admin_class_dashboard {
        return remoteDataSource.admingetdashboard()
    }
    suspend fun usergetlistchat(username: String): MutableList<Classuniversal_chat>   {
        return remoteDataSource.usergetlistchat(username)
    }
    suspend fun usergetbubble(id: Int): MutableList<Classuniversal_bubble>   {
        return remoteDataSource.usergetbubble(id)
    }

    suspend fun usergetartikel(): MutableList<Artikel> {
        return remoteDataSource.usergetartikel()
    }
    suspend fun getdokters(): MutableList<User> {
        return remoteDataSource.getdokters().filter { it.specialist != "" }.toMutableList()
    }

    suspend fun uploadArtikel(artikel:Artikel){
        val newData = remoteDataSource.uploadArtikel(artikel)
    }
    suspend fun updateDokterProfile(user: User) {
        val updateData = remoteDataSource.updateDokterProfile(user.username, user)
    }
    suspend fun sendpesanbiasa(idhchat: Int, pengirim: String, penerima: String, isi: String) {
        val chatBody = ChatBody(isi)
        val newData = remoteDataSource.sendpesanbiasa(idhchat, pengirim, penerima, chatBody)
    }
    suspend fun getlistfoodtrack(username: String): MutableList<Classuniversal_foodtrack> {
        return remoteDataSource.getlistfoodtrack(username)
    }
    
    suspend fun sendotp(dataotp:KirimOtp){
        val newData = remoteDataSource.sendotp(dataotp)
    }

    suspend fun changePassword(email:String, password:changePw){
        val newData = remoteDataSource.changePassword(email,password)
    }

    suspend fun updateUserProfile(user: User) {
        val updateData = remoteDataSource.updateUserProfile(user.username, user)
    }

    suspend fun updateViewArtikel(artikel:Artikel) {
        val updateData = remoteDataSource.updateViewArtikel(artikel)
    }

    suspend fun gethistoryreview(username_target: String): MutableList<Review> {
        return remoteDataSource.gethistoryreview(username_target)
    }

    suspend fun getAverageRating(username_target: String):Map<String, String> {
        return remoteDataSource.getAverageRating(username_target)
    }
    suspend fun changePassword(email:String, password:String){
        val newData = remoteDataSource.changePassword(email,password)
    }
    
    suspend fun sendpesanfoodtrack(idhchat: Int, pengirim: String, penerima: String, isi: String) {
        val chatBody = ChatBody(isi)
        val newData = remoteDataSource.sendpesanfoodtrack(idhchat, pengirim, penerima, chatBody)
    }
    suspend fun getgizidata(cari:String): Classuniversal_hasilgizi{
        return remoteDataSource.getgizidata(cari)
    }
    suspend fun tambahfoodtrack(username:String,isi: Bodyaddfoodtrack){
        return remoteDataSource.tambahfoodtrack(username,isi)
    }

    suspend fun createDokter(user: User) {
        val newData = remoteDataSource.createDokter(user)
    }
    suspend fun endchatdokter(idhcat:Int,usernamelawan:String,username:String,isi: String,rating:Float,kesimpulan:String) {
        val newData = remoteDataSource.endchatdokter(idhcat,usernamelawan,username,isi,rating,kesimpulan)
    }
}
