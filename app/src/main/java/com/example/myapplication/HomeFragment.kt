package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class HomeFragment : Fragment() {

    lateinit var container: FragmentContainerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: homeuserAdapter
    private lateinit var artikelList: MutableList<User_class_list_artikel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    // Handle home navigation
                    loadFragment(1)
                    true
                }
                R.id.search -> {
                    // Handle dashboard navigation
                    loadFragment(2)
                    true
                }
                R.id.profile -> {
                    // Handle notifications navigation
                    loadFragment(3)
                    true
                }
                else -> false
            }
        }

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

    private fun loadFragment(kode:Int) {
        if (kode == 1) {
            container.getFragment<Fragment>().findNavController()
                .navigate(R.id.action_global_homeFragment)
        } else if (kode == 2) {
            container.getFragment<Fragment>().findNavController()
                .navigate(R.id.action_global_searchFragment)
        } else {
            container.getFragment<Fragment>().findNavController()
                .navigate(R.id.action_global_myProfileFragment)
        }
    }

    private fun topFragment(kode:Int) {
        if (kode == 1) {
            container.getFragment<Fragment>().findNavController()
                .navigate(R.id.action_global_homeFragment)
        } else if (kode == 2) {
            container.getFragment<Fragment>().findNavController()
                .navigate(R.id.action_global_homeFragment)
        }
        else if (kode == 3) {
            container.getFragment<Fragment>().findNavController()
                .navigate(R.id.action_global_homeFragment)
        }else {
            container.getFragment<Fragment>().findNavController()
                .navigate(R.id.action_global_chatFragment)
        }
    }
}