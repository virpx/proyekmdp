package com.example.myapplication.Doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.MockDB
import com.example.myapplication.databinding.FragmentHistoryReviewDokterBinding
import com.example.myapplication.viewmodel.HistoryReviewDokterViewModel

data class Review(val username_pengirim: String, var username_target: String, var isi: String, var rating: Float)

class HistoryReviewDokterFragment : Fragment() {
    private lateinit var binding: FragmentHistoryReviewDokterBinding
    private val viewModel: HistoryReviewDokterViewModel by viewModels()
    private lateinit var adapter: HistoryReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryReviewDokterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HistoryReviewAdapter(emptyList<Review>().toMutableList())

        viewModel.reviewList.observe(viewLifecycleOwner, Observer { reviews ->
            adapter.updateData(reviews)
        })

        binding.historyReviewDokterRv.layoutManager = LinearLayoutManager(context)
        binding.historyReviewDokterRv.adapter = adapter

        viewModel.fetchReviewList(MockDB.usernamelogin)
    }
}

@BindingAdapter("reviewList")
fun bindReviewList(recyclerView: RecyclerView, reviews: List<Review>?) {
    val adapter = recyclerView.adapter as? HistoryReviewAdapter
    if (adapter != null && reviews != null) {
        adapter.updateData(reviews)
    }
}
