package com.example.myapplication

import java.time.format.DateTimeFormatter
import java.util.Date

class Classuniversal_chat(val idhcat:Int, val gambar:String,val username:String,val nama:String) {
}
class Classuniversal_foodtrack(val id:Int,val username: String,val nama:String,val jumlah:Int,val calories:Int,val protein:Int,val sugar:Int,val carbs:Int, val fat:Int,val cholesterol:Int,val sodium:Int,val date_add:String){

}
class Classuniversal_bubble(val nama_pengirim:String, val nama_penerima:String,val pengirim:String, val penerima:String, val isi:String,val attach_foodtrack:List<Classuniversal_foodtrack>){

}
data class ChatBody(val isi: String)

class Classuniversal_hasilgizi(
    val calories:Float,
    val protein:Float,
    val sugar:Float,
    val carbs:Float,
    val fat:Float,
    val cholesterol:Float,
    val sodium:Float
)

class Bodyaddfoodtrack(
    val nama:String,
    val jumlah:Int,
    val calories:Int,
    val protein:Int,
    val sugar:Int,
    val carbs:Int,
    val fat:Int,
    val cholesterol:Int,
    val sodium:Int,
)

class Recipesend(
    var nama_obat:String,
    var deskripsi_obat:String
)
class Sendreviewdokter(
    var isi:String,
    var rating:Float
)