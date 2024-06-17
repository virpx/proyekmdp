package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class homeuserAdapter(val data: MutableList<User_class_list_artikel>,) :
    RecyclerView.Adapter<homeuserAdapter.HomeViewHolder>() {


    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView13)
        val titleTextView: TextView = itemView.findViewById(R.id.textView53)
        val authorTextView: TextView = itemView.findViewById(R.id.textView50)
        val contentTextView: TextView = itemView.findViewById(R.id.textView51)
        val likeButton: Button = itemView.findViewById(R.id.button3)
        val dislikeButton: Button = itemView.findViewById(R.id.button4)
        val commentButton: Button = itemView.findViewById(R.id.button5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listhome, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val artikel = data[position]
        holder.titleTextView.text = artikel.judul
        holder.titleTextView.text = artikel.author
        holder.contentTextView.text = artikel.isi
        holder.likeButton.setOnClickListener {
            // Handle like button click
        }
        holder.dislikeButton.setOnClickListener {
            // Handle dislike button click
        }
        holder.commentButton.setOnClickListener {
            // Handle comment button click
        }
        // Set the image view if needed, example:
        // holder.imageView.setImageResource(R.drawable.some_image)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}