package com.example.myapplication.Admin

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
import com.example.myapplication.databinding.FragmentAdminListArtikelBinding
import com.example.myapplication.viewmodel.AdminListArtikelViewModel

class admin_list_artikel : Fragment() {
    private lateinit var binding: FragmentAdminListArtikelBinding
    private val viewModel: AdminListArtikelViewModel by viewModels()
    private lateinit var adapter: adminadapter_lartikel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminListArtikelBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = adminadapter_lartikel(emptyList<Admin_class_list_artikel>().toMutableList()) { idArtikel ->
            viewModel.deleteArticle(idArtikel) {
                viewModel.fetchArticles()
            }
        }

        binding.rvne.layoutManager = LinearLayoutManager(context)
        binding.rvne.adapter = adapter

        viewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            adapter.updateData(articles)
        })

        viewModel.fetchArticles()
    }
}

@BindingAdapter("reviewList")
fun bindReviewList(recyclerView: RecyclerView, articles: List<Admin_class_list_artikel>?) {
    val adapter = recyclerView.adapter as? adminadapter_lartikel
    if (adapter != null && articles != null) {
        adapter.updateData(articles)
    }
}
