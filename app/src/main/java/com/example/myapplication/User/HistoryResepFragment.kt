package com.example.myapplication.User

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.MockDB
import com.example.myapplication.databinding.FragmentHistoryResepBinding
import com.example.myapplication.main_user
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class Resep(val nama_obat:String, val deskripsi_obat:String)

class HistoryResepFragment : Fragment() {
    private lateinit var binding: FragmentHistoryResepBinding
    private val repository = main_user.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var obatAdapter: ObatAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using DataBindingUtil
        binding = FragmentHistoryResepBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.resepRv

        obatAdapter = ObatAdapter(mutableListOf())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = obatAdapter

        ioScope.launch {
            val resepList = repository.getResep(MockDB.currenthchat.username, MockDB.currenthchat
                .kesimpulan)
            withContext(Dispatchers.Main) {
                obatAdapter.updateData(resepList)
            }
        }
    }
}