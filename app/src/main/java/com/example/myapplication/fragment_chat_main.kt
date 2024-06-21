package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.Visibility
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.MockDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.util.Timer
import java.util.TimerTask

class fragment_chat_main : Fragment() {
    private val repository = MainActivity.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var rvne: RecyclerView
    lateinit var adminadapter: chatbubble
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var timer:Timer
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
    private fun base64ToBitmap(base64Str: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
            BitmapFactory.decodeStream(ByteArrayInputStream(decodedBytes))
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(MockDB.userloginrole == 1){
            view.findViewById<ImageView>(R.id.imageView9).visibility = View.INVISIBLE
        }else{
            view.findViewById<ImageView>(R.id.imageView5).visibility = View.GONE
        }
        idhcat = fragment_chat_mainArgs?.fromBundle(arguments as Bundle)?.idhcat!!
        var usernamelawan = fragment_chat_mainArgs?.fromBundle(arguments as Bundle)?.username!!
        view.findViewById<TextView>(R.id.textView27).text = MockDB.namaopenchat
        val bitmap = base64ToBitmap(MockDB.gambaropenchat)
        view.findViewById<ImageView>(R.id.imageView4).setImageBitmap(bitmap)
        rvne = view.findViewById(R.id.recyclerView2)
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        adminadapter = chatbubble(databubble,{
            findNavController().navigate(R.id.action_global_showfoodtrack)
        })
        rvne.adapter = adminadapter
        rvne.layoutManager = layoutManager
        getdata()
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                getdata()
            }
        }, 500, 500)
        view.findViewById<ImageView>(R.id.imageView6).setOnClickListener {
            var isichat = view.findViewById<TextView>(R.id.editTextText11).text.toString()
            if(isichat!= ""){
                ioScope.launch {
                    repository.sendpesanbiasa(idhcat,MockDB.usernamelogin,usernamelawan,isichat)
                    requireActivity().runOnUiThread {
                        view.findViewById<TextView>(R.id.editTextText11).text = ""
                        getdata()
                    }
                }
            }
        }
        view.findViewById<ImageView>(R.id.imageView9).setOnClickListener {
            val action = fragment_chat_mainDirections.actionGlobalChatChooseFoodTrack(usernamelawan,idhcat)
            findNavController().navigate(action)
        }
        view.findViewById<ImageView>(R.id.imageView5).setOnClickListener {
            val action = fragment_chat_mainDirections.actionGlobalEndchat(idhcat)
            findNavController().navigate(action)
        }
    }
    fun getdata(){
        ioScope.launch {
            databubble.clear()
            var hasil = repository.usergetbubble(idhcat)
            databubble.addAll(hasil)
            try {
                requireActivity().runOnUiThread {
                    adminadapter.notifyDataSetChanged()
                }
            }catch (e:IllegalStateException){
                timer.cancel()
            }
        }
    }
}