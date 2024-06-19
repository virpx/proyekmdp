package com.example.myapplication.User

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Classuniversal_foodtrack
import com.example.myapplication.R
import java.util.Date

data class FoodTrack(
    val id: Int,
    val username: String,
    val nama: String,
    val jumlah: Int,
    val calories: Int,
    val protein: Int,
    val sugar: Int,
    val carbs: Int,
    val fat: Int,
    val cholesterol: Int,
    val sodium: Int,
    val date_add: Date
)

class FoodTrackAdapter(var foodList: MutableList<Classuniversal_foodtrack>) :
    RecyclerView.Adapter<FoodTrackAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName: TextView = itemView.findViewById(R.id.tvFoodName)
        val calories: TextView = itemView.findViewById(R.id.tvCalories)
        val protein: TextView = itemView.findViewById(R.id.tvProtein)
        val sugar: TextView = itemView.findViewById(R.id.tvSugar)
        val carbs: TextView = itemView.findViewById(R.id.tvCarbs)
        val fat: TextView = itemView.findViewById(R.id.tvFat)
        val cholesterol: TextView = itemView.findViewById(R.id.tvCholesterol)
        val sodium: TextView = itemView.findViewById(R.id.tvSodium)
        val dateAdded: TextView = itemView.findViewById(R.id.tvDateAdded)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food_track, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodItem = foodList[position]
        holder.foodName.text = foodItem.nama
        holder.calories.text = "Calories: ${foodItem.calories}"
        holder.protein.text = "Protein: ${foodItem.protein}g"
        holder.sugar.text = "Sugar: ${foodItem.sugar}g"
        holder.carbs.text = "Carbs: ${foodItem.carbs}g"
        holder.fat.text = "Fat: ${foodItem.fat}g"
        holder.cholesterol.text = "Cholesterol: ${foodItem.cholesterol}mg"
        holder.sodium.text = "Sodium: ${foodItem.sodium}mg"
        holder.dateAdded.text = "Date Added: ${foodItem.date_add}"
    }

    override fun getItemCount() = foodList.size

    fun updateData(newFoodList: List<Classuniversal_foodtrack>) {
        foodList = newFoodList.toMutableList()
        notifyDataSetChanged()
    }
}
