package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.MockDB
import com.example.myapplication.Doctor.Artikel
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val repository = main_user.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: homeuserAdapter
    private lateinit var artikelList: MutableList<Artikel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView2 =
            view.findViewById<BottomNavigationView>(R.id.bottomNavigationView2)
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

        ioScope.launch {
            val fetchedArtikelList = repository.usergetartikel()

            withContext(Dispatchers.Main) {
                artikelList = fetchedArtikelList
                adapter = homeuserAdapter(artikelList) {
                    MockDB.activeArtikel = it

                    ioScope.launch {
                        withContext(Dispatchers.Main) {
                            repository.updateViewArtikel(it)
                            findNavController().navigate(HomeFragmentDirections.actionGlobalBacaArtikelFragment())
                        }
                    }

                }
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }

        recyclerView = view.findViewById(R.id.recyclerViewHome)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }


    private fun topFragment(kode: Int) {
        val navController = findNavController()
        when (kode) {
            1 -> navController.navigate(R.id.action_global_homeFragment)
            2 -> navController.navigate(R.id.action_global_homeFragment)
            3 -> navController.navigate(R.id.action_global_homeFragment)
            4 -> navController.navigate(R.id.action_global_fragment_user_listchat)
        }
    }
}
