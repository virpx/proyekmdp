package com.example.myapplication

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Doctor.Artikel

class homeuserAdapter(
    val data: MutableList<Artikel>,
    var onClickListener: ((Artikel)-> Unit)
) : RecyclerView.Adapter<homeuserAdapter.HomeViewHolder>() {


    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView13)
        val titleTextView: TextView = itemView.findViewById(R.id.textView53)
        val authorTextView: TextView = itemView.findViewById(R.id.textView50)
        val viewTextView: TextView = itemView.findViewById(R.id.textView5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listhome, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val artikel = data[position]
        holder.titleTextView.text = artikel.judul
        holder.authorTextView.text = "By: Dr. ${artikel.penulis}"
        holder.viewTextView.text = "Viewed: ${artikel.view} time(s)"

        if (artikel.image != "") {
            val base64Image = artikel.image
                ?: ""
            val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            holder.imageView.setImageBitmap(bitmap)
        }

        holder.itemView.setOnClickListener {
            onClickListener.invoke(artikel)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}