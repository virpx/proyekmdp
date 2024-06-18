package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.viewmodel.AdminListArtikelViewModel

class admin_list_artikel : Fragment() {
    private val viewModel: AdminListArtikelViewModel by viewModels()
    lateinit var rvne: RecyclerView
    lateinit var adminadapter: adminadapter_lartikel
    lateinit var layoutManager: RecyclerView.LayoutManager
    var dataartikele = mutableListOf<Admin_class_list_artikel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin_list_artikel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvne = view.findViewById(R.id.rvne)
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adminadapter = adminadapter_lartikel(dataartikele) { idartike ->
            viewModel.deleteArticle(idartike) {
                viewModel.fetchArticles()
            }
        }
        rvne.adapter = adminadapter
        rvne.layoutManager = layoutManager

        viewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            adminadapter.updateData(articles)
        })

        viewModel.fetchArticles()
    }
}
