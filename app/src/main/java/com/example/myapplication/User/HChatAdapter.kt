
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.User.HChat

class HChatAdapter(private val hChatList: MutableList<HChat>,
                   var onClick:((HChat)->Unit)) :
    RecyclerView.Adapter<HChatAdapter.HChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HChatViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kesimpulan, parent, false)
        return HChatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HChatViewHolder, position: Int) {
        val currentItem = hChatList[position]
        holder.fullName.text = "Dr. ${currentItem.fullName}"

        if (currentItem.fotoProfile != "") {
            val base64Image = currentItem.fotoProfile ?: ""
            val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            holder.fotoProfile.setImageBitmap(bitmap)
        }
        holder.kesimpulan.text = currentItem.kesimpulan
        holder.itemView.setOnClickListener {
           onClick.invoke(currentItem)
        }
    }

    override fun getItemCount() = hChatList.size

    class HChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fullName: TextView = itemView.findViewById(R.id.fullName)
        val fotoProfile: ImageView = itemView.findViewById(R.id.fotoProfile)
        val kesimpulan: TextView = itemView.findViewById(R.id.kesimpulan)
    }

    fun updateData(newHChatList: List<HChat>) {
        hChatList.clear()
        hChatList.addAll(newHChatList)
        notifyDataSetChanged()
    }
}
