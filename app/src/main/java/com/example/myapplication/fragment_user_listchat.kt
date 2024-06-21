package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.MockDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import android.util.Base64

class fragment_user_listchat : Fragment() {
    private val repository = MainActivity.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var rvne: RecyclerView
    lateinit var adminadapter: chatlist_adapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    var datachate = mutableListOf(
        Classuniversal_chat(-1,"","","")
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_listchat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvne = view.findViewById(R.id.rvne)
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        adminadapter = chatlist_adapter(datachate,{idhchat,username,namachat,profilelawan->
            MockDB.namaopenchat = namachat
            MockDB.usernamechatopen = username
            MockDB.gambaropenchat = profilelawan
            val action = fragment_user_listchatDirections.actionGlobalFragmentChatMain(idhchat,username)
            findNavController().navigate(action)
        })
        rvne.adapter = adminadapter
        rvne.layoutManager = layoutManager
        ioScope.launch {
            datachate.clear()
            var hasil = repository.usergetlistchat(MockDB.usernamelogin)
            datachate.addAll(hasil)
            requireActivity().runOnUiThread {
                adminadapter.notifyDataSetChanged()
            }
        }
    }
}