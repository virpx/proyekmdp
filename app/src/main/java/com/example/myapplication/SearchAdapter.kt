package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.MockDB
import com.example.myapplication.Database.User
import java.io.ByteArrayInputStream

//data class SearchAdapter(
//    val imageResId: Int,
//    val title: String
//)

class SearchAdapter(private var data: MutableList<User>, var onItemClick:((String,String,String,String,String,Int)->Unit)) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var originalList: List<User> = data.toList()

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textView56)
        var gambar:ImageView = itemView.findViewById(R.id.imageView20)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.searchdokter, parent, false)
        return SearchViewHolder(view)
    }
    private fun base64ToBitmap(base64Str: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
            BitmapFactory.decodeStream(ByteArrayInputStream(decodedBytes))
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = data[position]
        holder.titleTextView.text = item.username
        holder.itemView.setOnClickListener {
            onItemClick.invoke(item.username, item.fullname, item.email,item.sekolah,item.specialist,item.lama_praktik)
            MockDB.gambaropenchat = item.foto_profile
        }
        val bitmap = base64ToBitmap(item.foto_profile)
        holder.gambar.setImageBitmap(bitmap)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateList(newData: List<User>) {
        originalList = newData.toList()
        data = newData.toMutableList()
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        data = if (query.isEmpty()) {
            originalList.toMutableList()
        } else {
            originalList.filter {
                it.username.contains(query, ignoreCase = true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}