package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.MockDB

class chatlist_adapter(
    val data: MutableList<Classuniversal_chat>,
    var pindahroomchat:((Int,String,String)->Unit)
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
            pindahroomchat.invoke(datae.idhcat,datae.username,datae.nama)
        }
        holder.namauser.text = datae.nama
    }

}

class chatbubble(
    val data: MutableList<Classuniversal_bubble>,
    val showdetail:(()->Unit)
): RecyclerView.Adapter<chatbubble.ViewHolder>(){
    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val isi_penerima:TextView = row.findViewById(R.id.textView6)
        val container_isi_penerima:LinearLayout = row.findViewById(R.id.c_isi_penerima)
        val isi_pengirim:TextView = row.findViewById(R.id.textView9)
        val container_isi_pengirim:LinearLayout = row.findViewById(R.id.c_isi_pengirim)
        val container_food_pengirim:LinearLayout = row.findViewById(R.id.c_food_pengirim)
        val container_food_penerima:LinearLayout = row.findViewById(R.id.c_food_penerima)
        val fromtopenerima:TextView = row.findViewById(R.id.textView22)
        val fromtopengirim:TextView = row.findViewById(R.id.textView12)
        val showpengirim:LinearLayout = row.findViewById(R.id.btnshow_pengirim)
        val showpenerima:LinearLayout = row.findViewById(R.id.btnshow_penerima)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_bubble_chat_pasien_sender, parent ,false
        ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var datae = data[position]
        if(datae.isi == ""){
            holder.container_isi_penerima.visibility = View.GONE
            holder.container_isi_pengirim.visibility = View.GONE
            if(datae.pengirim == MockDB.usernamelogin){
                holder.container_food_pengirim.visibility = View.VISIBLE
                holder.container_food_penerima.visibility = View.GONE
                holder.fromtopengirim.text = "From: "+datae.attach_foodtrack[0].date_add+"\nTo: "+datae.attach_foodtrack[datae.attach_foodtrack.size-1].date_add
            }else{
                holder.container_food_pengirim.visibility = View.GONE
                holder.container_food_penerima.visibility = View.VISIBLE
                holder.fromtopenerima.text = "From: "+datae.attach_foodtrack[0].date_add+"\nTo: "+datae.attach_foodtrack[datae.attach_foodtrack.size-1].date_add
            }
        }else{
            holder.container_food_penerima.visibility = View.GONE
            holder.container_food_pengirim.visibility = View.GONE
            if(datae.pengirim == MockDB.usernamelogin){
                holder.container_isi_pengirim.visibility = View.VISIBLE
                holder.container_isi_penerima.visibility = View.GONE
                holder.isi_pengirim.text = datae.isi
            }else{
                holder.container_isi_pengirim.visibility = View.GONE
                holder.container_isi_penerima.visibility = View.VISIBLE
                holder.isi_penerima.text = datae.isi
            }
        }
        holder.showpengirim.setOnClickListener{
            MockDB.datashowfoodtrack = datae.attach_foodtrack
            showdetail.invoke()
        }
        holder.showpenerima.setOnClickListener{
            MockDB.datashowfoodtrack = datae.attach_foodtrack
            showdetail.invoke()
        }
        holder.isi_penerima.text = datae.isi
        holder.container_isi_pengirim
    }

}

class list_foodtrack(
    val data: MutableList<Classuniversal_foodtrack>,
    var cekid:((Int,Boolean)->Unit)
): RecyclerView.Adapter<list_foodtrack.ViewHolder>(){
    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val checkbox:CheckBox = row.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_chat_list_food_track_send, parent ,false
        ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var datae = data[position]
        holder.checkbox.text = datae.nama+",jumlah : "+datae.jumlah.toString()+"g ("+datae.date_add.toString()+")"
        holder.checkbox.setOnClickListener {
            cekid.invoke(datae.id,holder.checkbox.isChecked)
        }
    }

}

class showfoodtracke(
    val data: List<Classuniversal_foodtrack>,
): RecyclerView.Adapter<showfoodtracke.ViewHolder>(){
    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val namamakanan = row.findViewById<TextView>(R.id.textView28)
        val t1 = row.findViewById<TextView>(R.id.textView29)
        val t2 = row.findViewById<TextView>(R.id.textView51)
        val t3 = row.findViewById<TextView>(R.id.textView58)
        val t4 = row.findViewById<TextView>(R.id.textView60)
        val t5 = row.findViewById<TextView>(R.id.textView64)
        val t6 = row.findViewById<TextView>(R.id.textView65)
        val t7 = row.findViewById<TextView>(R.id.textView66)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_show_foodtrack, parent ,false
        ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var datae = data[position]
        holder.namamakanan.text = datae.nama+" ("+datae.jumlah.toString()+"g)"
        holder.t1.text = "calories: "+datae.calories
        holder.t2.text = "protein: "+datae.protein
        holder.t3.text = "sugar: "+datae.sugar
        holder.t4.text = "carbs: "+datae.carbs
        holder.t5.text = "fat: "+datae.fat
        holder.t6.text = "cholesterol: "+datae.cholesterol
        holder.t7.text = "sodium: "+datae.sodium
    }

}