package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class SearchItem(
    val imageResId: Int,
    val title: String
)

class SearchAdapter(private val data: MutableList<User_class_list_dokter>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView20)
        val titleTextView: TextView = itemView.findViewById(R.id.textView56)
        val chatButton: Button = itemView.findViewById(R.id.button6)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.searchdokter, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = data[position]
        holder.titleTextView.text = item.username
        holder.chatButton.setOnClickListener {
            // Handle chat button click
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}