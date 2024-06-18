package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adminadapter {
}
class adminadapter_luser(
    var data: MutableList<Admin_class_list_user>,
    var pindahkedetail:((String)->Unit)
): RecyclerView.Adapter<adminadapter_luser.ViewHolder>(){

    fun updateData(newData: List<Admin_class_list_user>) {
        data = newData.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val namauser:TextView = row.findViewById(R.id.textView35)
        val username:TextView = row.findViewById(R.id.textView36)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_list_user_admin, parent ,false
        ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var datae = data[position]
        holder.itemView.setOnClickListener {
            pindahkedetail.invoke(datae.username)
        }
        holder.namauser.text = datae.nama
        holder.username.text = datae.username
    }

}

class adminadapter_lreview(
    var data: MutableList<Admin_class_review_user>,
): RecyclerView.Adapter<adminadapter_lreview.ViewHolder>(){

    fun updateData(newData: List<Admin_class_review_user>) {
        data = newData.toMutableList()
        notifyDataSetChanged()
    }
    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val namauser: TextView= itemView.findViewById(R.id.usernameTvReviewHistory)
        val rating: RatingBar = itemView.findViewById(R.id.ratingBarHistoryReview)
        val isi: TextView = itemView.findViewById(R.id.isiTvReviewHistory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_history_review_dokter, parent ,false
        ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var datae = data[position]
        holder.namauser.text = datae.nama
        holder.isi.text = datae.isi
        holder.rating.rating = datae.rating.toString().toFloat()
    }

}

class adminadapter_lartikel(
    val data: MutableList<Admin_class_list_artikel>,
    val deleteartikel : ((Int)->Unit),
): RecyclerView.Adapter<adminadapter_lartikel.ViewHolder>(){
    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val judul:TextView = row.findViewById(R.id.textView41)
        val author:TextView = row.findViewById(R.id.textView44)
        val jumlahview:TextView = row.findViewById(R.id.textView45)
        val btndel:ImageView = row.findViewById(R.id.imageView14)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_list_artikel_admin, parent ,false
        ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var datae = data[position]
        if(datae.judul.length > 27){
            holder.judul.text = datae.judul.substring(0,27)+"..."
        }else{
            holder.judul.text = datae.judul
        }
        holder.author.text = "Author : "+datae.author
        holder.jumlahview.text = "View : "+datae.view.toString()
        holder.btndel.setOnClickListener {
            deleteartikel.invoke(datae.id)
        }
    }

}

class adminadapter_lregisdokter(
    val data: MutableList<Admin_class_list_regis_dokter>,
    val deletedokter : ((String)->Unit),
    val accdokter : ((String)->Unit)
): RecyclerView.Adapter<adminadapter_lregisdokter.ViewHolder>(){
    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val nama:TextView = row.findViewById(R.id.textView46)
        val sekolahlulus:TextView = row.findViewById(R.id.textView47)
        val praktik:TextView = row.findViewById(R.id.textView48)
        val specialist:TextView = row.findViewById(R.id.textView49)
        val btndel:ImageView = row.findViewById(R.id.imageView16)
        val btnacc:ImageView = row.findViewById(R.id.imageView19)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_admin_list_dokter_regis, parent ,false
        ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var datae = data[position]
        holder.nama.text = datae.nama
        holder.sekolahlulus.text = datae.sekolahlulus
        holder.praktik.text = "Lama praktik : "+datae.lama_praktik
        holder.specialist.text = "Specialist : "+datae.specialist
        holder.btndel.setOnClickListener {
            deletedokter.invoke(datae.username)
        }
        holder.btnacc.setOnClickListener {
            accdokter.invoke(datae.username)
        }
    }

}