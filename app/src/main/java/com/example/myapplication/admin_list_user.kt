package com.example.myapplication

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class admin_list_user : Fragment() {
    private val repository = MainActivity.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var rvne: RecyclerView
    lateinit var adminadapter: adminadapter_luser
    lateinit var layoutManager: RecyclerView.LayoutManager
    var pilih = 0;
    var datausere = mutableListOf(
        Admin_class_list_user("",-1,"")
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_list_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvne = view.findViewById(R.id.rvlistuser)
        view.findViewById<TextView>(R.id.textView38).setOnClickListener {
            view.findViewById<TextView>(R.id.textView38).setTypeface(null,Typeface.BOLD)
            view.findViewById<TextView>(R.id.textView37).setTypeface(null,Typeface.NORMAL)
            pilih = 1
            getdatauser()
        }
        view.findViewById<TextView>(R.id.textView37).setOnClickListener {
            view.findViewById<TextView>(R.id.textView38).setTypeface(null,Typeface.NORMAL)
            view.findViewById<TextView>(R.id.textView37).setTypeface(null,Typeface.BOLD)
            pilih = 0
            getdatauser()
        }
        rvne.findViewById<RecyclerView>(R.id.rvlistuser)
        layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        adminadapter = adminadapter_luser(datausere,{username->
            val action = admin_list_userDirections.actionGlobalDetailUserAdmin(username)
            findNavController().navigate(action)
        })
        rvne.adapter = adminadapter
        rvne.layoutManager = layoutManager
        getdatauser()
    }
    fun getdatauser(){
        ioScope.launch {
            datausere.clear()
            var hasil = repository.admingetluser()
            hasil.forEach { hasile ->
                if(hasile.jenis == pilih){
                    datausere.add(hasile)
                }
            }
            requireActivity().runOnUiThread {
                adminadapter.notifyDataSetChanged()
            }
        }
    }
}