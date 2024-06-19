package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SearchFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private var searchItemList = mutableListOf<User>()
    private val repository = main_user.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewSearch)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SearchAdapter(searchItemList)

        ioScope.launch {
            try {
                val dok = repository.getdokters()
                println(dok)
                withContext(Dispatchers.Main) {
                    searchItemList = dok.toMutableList()
                    adapter = SearchAdapter(searchItemList)
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    // Handle HTTP errors here, e.g., show a Toast or Snackbar
                    Toast.makeText(context, "Failed to load data: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // Handle general errors here
                    Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Initialize RecyclerView


        // Set up EditText for search
//        val searchEditText = view.findViewById<EditText>(R.id.editTextText6)
//        searchEditText.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                // No-op
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                // No-op
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                adapter.filter(s.toString())
//            }
//        })
    }
}
