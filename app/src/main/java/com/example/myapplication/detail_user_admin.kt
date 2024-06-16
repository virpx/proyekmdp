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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class detail_user_admin : Fragment() {
    private val repository = MainActivity.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var rvne: RecyclerView
    lateinit var adminadapter: adminadapter_lreview
    lateinit var layoutManager: RecyclerView.LayoutManager
    var datareview = mutableListOf(
        Admin_class_review_user("","")
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_user_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val usernamee = detail_user_adminArgs?.fromBundle(arguments as Bundle)?.username
        rvne = view.findViewById(R.id.recyclerView3)
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        adminadapter = adminadapter_lreview(datareview)
        rvne.adapter = adminadapter
        rvne.layoutManager = layoutManager
        ioScope.launch {
            datareview.clear()
            var hasil = repository.admingetuserreview(usernamee!!)
            datareview.addAll(hasil)
            var datauser = repository.getUserByUsername(usernamee!!)
            requireActivity().runOnUiThread {
                adminadapter.notifyDataSetChanged()
                view.findViewById<TextView>(R.id.textView39).text = datauser.fullname
                if(datauser.specialist == ""){
                    view.findViewById<TextView>(R.id.textView40).text = "Standard"
                }
            }
        }
    }
}