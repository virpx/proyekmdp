package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.User

//data class SearchAdapter(
//    val imageResId: Int,
//    val title: String
//)

class SearchAdapter(private var data: MutableList<User>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var originalList: List<User> = data.toList()

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textView56)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.searchdokter, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = data[position]
        holder.titleTextView.text = item.username
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