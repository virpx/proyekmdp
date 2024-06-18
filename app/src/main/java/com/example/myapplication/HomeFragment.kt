package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: homeuserAdapter
    private lateinit var artikelList: MutableList<User_class_list_artikel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView2 = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView2)
        bottomNavigationView2.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.app -> {
                    // Handle home navigation
                    topFragment(1)
                    true
                }
                R.id.app2 -> {
                    // Handle dashboard navigation
                    topFragment(2)
                    true
                }
                R.id.app3 -> {
                    // Handle notifications navigation
                    topFragment(3)
                    true
                }
                R.id.chats -> {
                    // Handle notifications navigation
                    topFragment(4)
                    true
                }
                else -> false
            }
        }
        artikelList = mutableListOf() // Initialize the list

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewHome)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = homeuserAdapter(artikelList)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun topFragment(kode: Int) {
        val navController = findNavController()
        when (kode) {
            1 -> navController.navigate(R.id.action_global_homeFragment)
            2 -> navController.navigate(R.id.action_global_homeFragment)
            3 -> navController.navigate(R.id.action_global_homeFragment)
//            4 -> navController.navigate(R.id.action_global_chatFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
