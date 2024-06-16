package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adminadapter {
}
class adminadapter_luser(
    val data: MutableList<Admin_class_list_user>,
    var pindahkedetail:((String)->Unit)
): RecyclerView.Adapter<adminadapter_luser.ViewHolder>(){
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
    val data: MutableList<Admin_class_review_user>,
): RecyclerView.Adapter<adminadapter_lreview.ViewHolder>(){
    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val namauser:TextView = row.findViewById(R.id.textView42)
        val isi:TextView = row.findViewById(R.id.textView43)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_admin_user_review, parent ,false
        ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var datae = data[position]
        holder.namauser.text = datae.nama
        holder.isi.text = datae.isi
    }

}