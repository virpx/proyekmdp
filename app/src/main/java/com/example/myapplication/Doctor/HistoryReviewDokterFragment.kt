package com.example.myapplication.Doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.MockDB
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHistoryReviewDokterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class Review(val username_pengirim:String, var username_target:String, var isi:String, var
rating:Float)

class HistoryReviewDokterFragment : Fragment() {
    private lateinit var binding: FragmentHistoryReviewDokterBinding
    private val repository = main_dokter.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryReviewAdapter
    private lateinit var reviewList: MutableList<Review>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryReviewDokterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ioScope.launch {
            val fetchedReviewList = repository.gethistoryreview(MockDB.usernamelogin)

            withContext(Dispatchers.Main) {
                reviewList = fetchedReviewList
                adapter = HistoryReviewAdapter(reviewList)

                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }

        recyclerView = view.findViewById(R.id.historyReviewDokterRv)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
}