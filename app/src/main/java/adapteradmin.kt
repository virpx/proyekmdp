import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Admin_class_list_user
import com.example.myapplication.Admin_class_review_user
import com.example.myapplication.R

class adapteradmin_listuser(
    val data: MutableList<Admin_class_list_user>,
): RecyclerView.Adapter<adapteradmin_listuser.ViewHolder>() {
    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val namauser = row.findViewById<TextView>(R.id.textView35)
        val username = row.findViewById<TextView>(R.id.textView36)
        val btndelete = row.findViewById<ImageView>(R.id.imageView13)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_list_user_admin, parent ,false
        ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var datae = data[position]
        holder.namauser.text = datae.nama
        holder.username.text = datae.username
        holder.btndelete.setOnClickListener {

        }
    }

}

class adapteradmin_reviewuser(
    val data: MutableList<Admin_class_review_user>,
): RecyclerView.Adapter<adapteradmin_reviewuser.ViewHolder>() {
    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val namauser = row.findViewById<TextView>(R.id.textView42)
        val review = row.findViewById<TextView>(R.id.textView43)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_admin_user_review, parent ,false
        ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var datae = data[position]
        holder.namauser.text = datae.nama
        holder.review.text = datae.isi
    }
}