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
import com.example.myapplication.viewmodel.AdminListRegisDokterViewModel

class admin_list_regis_dokter : Fragment() {
    private val viewModel: AdminListRegisDokterViewModel by viewModels()
    lateinit var rvne: RecyclerView
    lateinit var adminadapter: adminadapter_lregisdokter
    lateinit var layoutManager: RecyclerView.LayoutManager
    var datadoktere = mutableListOf<Admin_class_list_regis_dokter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin_list_regis_dokter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvne = view.findViewById(R.id.rvne)
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adminadapter = adminadapter_lregisdokter(datadoktere, { username ->
            viewModel.deleteDoctor(username) {
                viewModel.fetchDoctors()
            }
        }, { username ->
            viewModel.acceptDoctor(username) {
                viewModel.fetchDoctors()
            }
        })
        rvne.adapter = adminadapter
        rvne.layoutManager = layoutManager

        // Observe LiveData from ViewModel
        viewModel.doctors.observe(viewLifecycleOwner, Observer { doctors ->
            adminadapter.updateData(doctors)
        })

        // Fetch doctors
        viewModel.fetchDoctors()
    }
}
