package com.example.myapplication.User

import HChatAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.MockDB
import com.example.myapplication.databinding.FragmentHistoryKesimpulanBinding
import com.example.myapplication.main_user
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class HChat(
    val username:String,
    val fullName: String,
    val fotoProfile: String,
    val selesai:Int,
    val kesimpulan: String
)

class HistoryKesimpulanFragment : Fragment() {
    private lateinit var binding: FragmentHistoryKesimpulanBinding
    private val repository = main_user.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var hChatAdapter: HChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using DataBindingUtil
        binding = FragmentHistoryKesimpulanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = binding.kesimpulanRv

        hChatAdapter = HChatAdapter(mutableListOf(),{
            MockDB.currenthchat = it
            MockDB.usernamechatopen = it.username
            findNavController().navigate(HistoryKesimpulanFragmentDirections.actionGlobalHistoryResepFragment())
        },{
            findNavController().navigate(HistoryKesimpulanFragmentDirections.actionGlobalReviewdokter())
        })
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = hChatAdapter

        ioScope.launch {
            val hChatList = repository.getHchat(MockDB.usernamelogin)
            withContext(Dispatchers.Main) {
                hChatAdapter.updateData(hChatList)
            }
        }
    }
}