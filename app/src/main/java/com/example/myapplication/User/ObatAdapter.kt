package com.example.myapplication.User

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ObatAdapter(private val obatList: MutableList<Resep>) :
    RecyclerView.Adapter<ObatAdapter.ObatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObatViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_obat, parent, false)
        return ObatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ObatViewHolder, position: Int) {
        val currentItem = obatList[position]
        holder.namaObat.text = currentItem.nama_obat
        holder.deskripsiObat.text = currentItem.deskripsi_obat
    }

    override fun getItemCount() = obatList.size

    class ObatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaObat: TextView = itemView.findViewById(R.id.namaObat)
        val deskripsiObat: TextView = itemView.findViewById(R.id.deskripsiObat)
    }

    fun updateData(newHChatList: List<Resep>) {
        obatList.clear()
        obatList.addAll(newHChatList)
        notifyDataSetChanged()
    }
}
