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

class SearchAdapter(var data: MutableList<User>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private var filteredList: List<User> = data.toList()

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

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            data
        } else {
            val filteredList = data.filter {
                it.username.contains(query, ignoreCase = true)
            }
            filteredList.toMutableList()

        }
        data = filteredList.toMutableList()
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textView56)

        fun bind(user: User) {
            nameTextView.text = user.username
        }
    }
}