package com.example.myapplication.User

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentHistoryMakananBinding
import com.example.myapplication.viewmodel.FoodTrackViewModel

class HistoryMakananFragment : Fragment() {
    private lateinit var binding: FragmentHistoryMakananBinding
    private val viewModel: FoodTrackViewModel by viewModels()
    private lateinit var adapter: FoodTrackAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryMakananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FoodTrackAdapter(mutableListOf())
        binding.recyclerViewFoodTrack.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFoodTrack.adapter = adapter

        viewModel.foodList.observe(viewLifecycleOwner, Observer { foodList ->
            adapter.updateData(foodList)
        })

        viewModel.fetchFoodList()
    }
}
