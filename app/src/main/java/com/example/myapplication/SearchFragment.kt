package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private val searchItemList = mutableListOf<User_class_list_dokter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SearchAdapter(searchItemList)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        val navbatom = view.findViewById<BottomNavigationView>(R.id.BottomNavigationViewSearch)
        navbatom.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.home -> {
                    // Handle home navigation
                    findNavController().navigate(R.id.action_global_homeFragment)
                    true
                }
                R.id.search -> {
                    // Handle dashboard navigation
                    findNavController().navigate(R.id.action_global_searchFragment)
                    true
                }
                R.id.profile -> {
                    // Handle notifications navigation
                    findNavController().navigate(R.id.action_global_myProfileFragment)
                    true
                }
                else -> false
            }
        }

        // Set up EditText for search
        val searchEditText = view.findViewById<EditText>(R.id.editTextText6)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // No-op
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No-op
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s.toString())
            }
        })
    }
}
