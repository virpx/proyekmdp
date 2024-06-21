package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.Database.MockDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class Hcat (var id:Int, var user1:String, var user2:String, var selesai:Int, var
kesimpulan:String)
class ProfilFragment : Fragment() {

    private val args: ProfilFragmentArgs by navArgs()
    private lateinit var nameTextView: TextView
    private lateinit var fl: TextView
    private lateinit var sk: TextView
    private lateinit var lm: TextView
    private lateinit var sp: TextView
    private lateinit var em: TextView
    private lateinit var gochat: Button
    private val repository = MainActivity.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameTextView = view.findViewById(R.id.textView67)
        fl = view.findViewById(R.id.textView73)
        sk = view.findViewById(R.id.textView74)
        lm = view.findViewById(R.id.textView75)
        sp = view.findViewById(R.id.textView76)
        em = view.findViewById(R.id.textView77)
        gochat = view.findViewById(R.id.button5)
        val username = args.username
        val fullname = args.fullname
        val sekolah = args.sekolah
        val email = args.email
        val specialist = args.specialist
        val lamaPraktik = args.lamaPraktik
        nameTextView.text = username
        fl.text = fullname
        sk.text = sekolah
        lm.text = lamaPraktik.toString()
        sp.text = specialist
        em.text = email
        var ids = ""
        var idhcat = ""
        gochat.setOnClickListener(){
            ioScope.launch {
                var newHcat = Hcat(
                    id = id.toInt()+1,
                    user1 = "",
                    user2 = username,
                    selesai = 0,
                    kesimpulan = ""
                )
                repository.createHcat(newHcat)
                ids = newHcat.id.toString()
            }
            MockDB.namaopenchat = username
            idhcat = ids
            val action = ProfilFragmentDirections.actionGlobalFragmentChatMain(idhcat.toInt(),username)
            findNavController().navigate(action)
        }

    }
}