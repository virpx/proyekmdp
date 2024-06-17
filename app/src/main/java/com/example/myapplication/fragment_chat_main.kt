package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.MockDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class fragment_chat_main : Fragment() {
    private val repository = MainActivity.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var rvne: RecyclerView
    lateinit var adminadapter: chatbubble
    lateinit var layoutManager: RecyclerView.LayoutManager
    var idhcat:Int = -1
    var pilih = 0;
    var databubble = mutableListOf(
        Classuniversal_bubble("","","","","", arrayListOf())
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idhcat = fragment_chat_mainArgs?.fromBundle(arguments as Bundle)?.idhcat!!
        view.findViewById<TextView>(R.id.textView27).text = MockDB.namaopenchat
        rvne = view.findViewById(R.id.recyclerView2)
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        adminadapter = chatbubble(databubble)
        rvne.adapter = adminadapter
        rvne.layoutManager = layoutManager
        ioScope.launch {
            databubble.clear()
            var hasil = repository.usergetbubble(idhcat)
            databubble.addAll(hasil)
            requireActivity().runOnUiThread {
                adminadapter.notifyDataSetChanged()
            }
        }
    }
}