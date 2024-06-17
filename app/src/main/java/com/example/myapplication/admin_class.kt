package com.example.myapplication

import org.w3c.dom.Text

class Admin_class {
}
class Admin_class_review_user(val nama:String,val isi:String){

}
class Admin_class_list_user(val nama:String,val jenis:Int,val username:String){

}
class Admin_class_list_artikel(val id:Int,val judul: String,val author:String,val view:Int){

}
class Admin_class_list_regis_dokter(
    val username:String,
    val nama:String,
    val sekolahlulus:String,
    val lama_praktik:Int,
    val specialist:String
){

}