package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class chatlist_adapter(
    val data: MutableList<Classuniversal_chat>,
    var pindahroomchat:((String)->Unit)
): RecyclerView.Adapter<chatlist_adapter.ViewHolder>(){
    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val namauser: TextView = row.findViewById(R.id.textView52)
        val profileuser: ImageView = row.findViewById(R.id.imageView18)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_list_chat, parent ,false
        ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var datae = data[position]
        holder.itemView.setOnClickListener {
            pindahroomchat.invoke(datae.username)
        }
        holder.namauser.text = datae.nama
    }

}