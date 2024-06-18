package com.example.myapplication

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.viewmodel.AdminListUserViewModel

class admin_list_user : Fragment() {
    private val viewModel: AdminListUserViewModel by viewModels()
    lateinit var rvne: RecyclerView
    lateinit var adminadapter: adminadapter_luser
    lateinit var layoutManager: RecyclerView.LayoutManager
    var pilih = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin_list_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvne = view.findViewById(R.id.rvlistuser)
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adminadapter = adminadapter_luser(mutableListOf()) { username ->
            val action = admin_list_userDirections.actionGlobalDetailUserAdmin(username)
            findNavController().navigate(action)
        }
        rvne.adapter = adminadapter
        rvne.layoutManager = layoutManager

        view.findViewById<TextView>(R.id.textView38).setOnClickListener {
            view.findViewById<TextView>(R.id.textView38).setTypeface(null, Typeface.BOLD)
            view.findViewById<TextView>(R.id.textView37).setTypeface(null, Typeface.NORMAL)
            pilih = 1
            viewModel.fetchData(pilih)
        }

        view.findViewById<TextView>(R.id.textView37).setOnClickListener {
            view.findViewById<TextView>(R.id.textView38).setTypeface(null, Typeface.NORMAL)
            view.findViewById<TextView>(R.id.textView37).setTypeface(null, Typeface.BOLD)
            pilih = 0
            viewModel.fetchData(pilih)
        }

        viewModel.userData.observe(viewLifecycleOwner, Observer { users ->
            adminadapter.updateData(users)
        })

        viewModel.fetchData(pilih)
    }
}
