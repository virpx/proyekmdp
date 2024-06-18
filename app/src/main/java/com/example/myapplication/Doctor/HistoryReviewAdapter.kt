package com.example.myapplication.Doctor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class HistoryReviewAdapter(
    var data: MutableList<Review>,
) : RecyclerView.Adapter<HistoryReviewAdapter.HomeViewHolder>() {

    fun updateData(newData: List<Review>) {
        data = newData.toMutableList()
        notifyDataSetChanged()
    }


    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView= itemView.findViewById(R.id.usernameTvReviewHistory)
        val rating: RatingBar = itemView.findViewById(R.id.ratingBarHistoryReview)
        val isi: TextView = itemView.findViewById(R.id.isiTvReviewHistory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_history_review_dokter, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val review = data[position]

        holder.username.text = review.username_pengirim
        holder.isi.text = review.isi
        holder.rating.rating = review.rating.toString().toFloat()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}