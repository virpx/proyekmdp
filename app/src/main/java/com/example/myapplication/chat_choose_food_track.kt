package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        rvne = view.findViewById(R.id.rvne)
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        adminadapter = list_foodtrack(datafoodtracke,{
            ide,status->

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
    }
}