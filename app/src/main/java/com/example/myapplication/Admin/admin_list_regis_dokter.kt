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
import com.example.myapplication.databinding.FragmentAdminListRegisDokterBinding
import com.example.myapplication.viewmodel.AdminListRegisDokterViewModel

class admin_list_regis_dokter : Fragment() {
    private lateinit var binding: FragmentAdminListRegisDokterBinding
    private val viewModel: AdminListRegisDokterViewModel by viewModels()
    private lateinit var adapter: adminadapter_lregisdokter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminListRegisDokterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = adminadapter_lregisdokter(emptyList<Admin_class_list_regis_dokter>().toMutableList(), { username ->
            viewModel.deleteDoctor(username) {
                viewModel.fetchDoctors()
            }
        }, { username ->
            viewModel.acceptDoctor(username) {
                viewModel.fetchDoctors()
            }
        })

        binding.rvne.layoutManager = LinearLayoutManager(context)
        binding.rvne.adapter = adapter

        viewModel.doctors.observe(viewLifecycleOwner, Observer { doctors ->
            adapter.updateData(doctors)
        })

        viewModel.fetchDoctors()
    }
}

@BindingAdapter("doctorList")
fun bindDoctorList(recyclerView: RecyclerView, doctors: List<Admin_class_list_regis_dokter>?) {
    val adapter = recyclerView.adapter as? adminadapter_lregisdokter
    if (adapter != null && doctors != null) {
        adapter.updateData(doctors)
    }
}
