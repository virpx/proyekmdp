package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Admin.admin_list_userDirections
import com.example.myapplication.Admin.adminadapter_luser
import com.example.myapplication.Database.MockDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class endchat : Fragment() {
    private val repository = MainActivity.Repository
    private val ioScope = CoroutineScope(Dispatchers.IO)
    lateinit var rvne: RecyclerView
    lateinit var adminadapter: addrecipe
    lateinit var layoutManager: RecyclerView.LayoutManager
    var lresep = mutableListOf<Recipesend>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_endchat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var idhcat = endchatArgs?.fromBundle(arguments as Bundle)?.idhchat!!
        rvne = view.findViewById(R.id.rvne)
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adminadapter = addrecipe(lresep, { id,nama,deskripsi ->
            lresep[id].nama_obat = nama
            lresep[id].deskripsi_obat = deskripsi
        },{
            id->
            lresep.removeAt(id)
            adminadapter.notifyDataSetChanged()
        })
        rvne.adapter = adminadapter
        rvne.layoutManager = layoutManager
        view.findViewById<Button>(R.id.button3).setOnClickListener {
            lresep.add(Recipesend("",""))
            adminadapter.notifyDataSetChanged()
        }
        view.findViewById<Button>(R.id.button).setOnClickListener {
            var isresep = false
            var isrun = true
            if(lresep.size != 0){
                isresep = true
                for (i in 0..lresep.size-1){
                    if(lresep[i].nama_obat == "" || lresep[i].deskripsi_obat == ""){
                        isrun = false
                        break
                    }
                }
            }
            if(isrun){
                var reviewuser = view.findViewById<TextView>(R.id.editTextText).text.toString()
                var kesimpulan = view.findViewById<TextView>(R.id.editTextText2).text.toString()
                var ratinguser = view.findViewById<TextView>(R.id.editTextNumberDecimal).text.toString()
                if(reviewuser != "" && ratinguser != "" && kesimpulan != ""){
                    var ratinguserr = view.findViewById<TextView>(R.id.editTextNumberDecimal).text.toString().toFloat()
                    if(ratinguserr >=1 && ratinguserr<=5){
                        ioScope.launch {
                            repository.endchatdokter(idhcat,MockDB.usernamechatopen,MockDB.usernamelogin,reviewuser,ratinguserr,kesimpulan)
                            if(isresep){
                                repository.dokteraddresep(idhcat,lresep)
                            }
                            requireActivity().runOnUiThread {
                                findNavController().navigate(R.id.action_global_fragment_user_listchat)
                            }
                        }
                    }
                }
            }
        }
    }
}
