package com.example.myapplication.Database

import com.example.myapplication.Classuniversal_foodtrack
import com.example.myapplication.Doctor.Artikel

object MockDB {
    var usernamelogin:String = "yoanesrobah"
    var usernamechatopen:String = ""
    var namaopenchat:String = ""
    var gambaropenchat:String = ""
    var activeArtikel:Artikel = Artikel("", "", "", 0,"")
    //0 = standar
    //1 = dokter
    var userloginrole = 0
    lateinit var datashowfoodtrack:List<Classuniversal_foodtrack>
}