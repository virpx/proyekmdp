package com.example.myapplication.Admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.viewmodel.DetailUserAdminViewModel

class detail_user_admin : Fragment() {
    private val viewModel: DetailUserAdminViewModel by viewModels()
    lateinit var rvne: RecyclerView
    lateinit var adminadapter: adminadapter_lreview
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var usernamee: String
    var datareview = mutableListOf<Admin_class_review_user>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_user_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usernamee = detail_user_adminArgs.fromBundle(arguments as Bundle).username
        rvne = view.findViewById(R.id.recyclerView3)
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adminadapter = adminadapter_lreview(datareview)
        rvne.adapter = adminadapter
        rvne.layoutManager = layoutManager

        viewModel.userData.observe(viewLifecycleOwner, Observer { user ->
            view.findViewById<TextView>(R.id.textView39).text = user.fullname
            view.findViewById<TextView>(R.id.textView40).text = if (user.specialist.isEmpty()) "Standard" else user.specialist
        })

        viewModel.reviews.observe(viewLifecycleOwner, Observer { reviews ->
            adminadapter.updateData(reviews)
        })

        viewModel.fetchUserDetails(usernamee)
        viewModel.fetchUserReviews(usernamee)

        view.findViewById<Button>(R.id.button2).setOnClickListener {
            viewModel.deleteUser(usernamee) {
                requireActivity().runOnUiThread {
                    findNavController().navigate(R.id.action_global_admin_list_user)
                }
            }
        }
    }
}
