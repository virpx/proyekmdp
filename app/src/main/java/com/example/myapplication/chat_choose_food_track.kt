package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.MockDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class chat_choose_food_track : Fragment() {
    private val repository = MainActivity.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var rvne: RecyclerView
    lateinit var adminadapter: list_foodtrack
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var usernamelawan:String
    var idhcat = -1
    var datafoodtracke = mutableListOf(
        Classuniversal_foodtrack(-1,"","",0,0,0,0,0,0,0,0,"")
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_choose_food_track, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usernamelawan = chat_choose_food_trackArgs?.fromBundle(arguments as Bundle)?.usernamelawan!!
        idhcat = chat_choose_food_trackArgs?.fromBundle(arguments as Bundle)?.idhchat!!
        rvne = view.findViewById(R.id.rvne)
        var listidpilih = mutableListOf<Int>()
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        adminadapter = list_foodtrack(datafoodtracke,{
            ide,status->
            if(status){
                listidpilih.add(ide)
            }else{
                listidpilih.removeIf { it == ide }
            }
        })
        rvne.adapter = adminadapter
        rvne.layoutManager = layoutManager
        ioScope.launch {
            datafoodtracke.clear()
            var hasil = repository.getlistfoodtrack(MockDB.usernamelogin)
            datafoodtracke.addAll(hasil)
            requireActivity().runOnUiThread {
                adminadapter.notifyDataSetChanged()
            }
        }
        view.findViewById<Button>(R.id.button7).setOnClickListener {
            if(listidpilih.size != 0){
                var listidpilihbaru = listidpilih.sorted()
                ioScope.launch {
                    repository.sendpesanfoodtrack(idhcat,MockDB.usernamelogin,usernamelawan,listidpilihbaru.joinToString(separator = ","))
                    requireActivity().runOnUiThread {
                        val action = chat_choose_food_trackDirections.actionGlobalFragmentChatMain(idhcat,usernamelawan)
                        findNavController().navigate(action)
                    }
                }
            }else{
                val action = chat_choose_food_trackDirections.actionGlobalFragmentChatMain(idhcat,usernamelawan)
                findNavController().navigate(action)
            }
        }
    }
}