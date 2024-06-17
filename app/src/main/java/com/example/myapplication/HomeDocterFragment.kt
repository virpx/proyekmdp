package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentHomeDocterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeDocterFragment : Fragment() {
    private lateinit var binding: FragmentHomeDocterBinding
    private val repository = main_dokter.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using DataBindingUtil
        binding = FragmentHomeDocterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ioScope.launch {
            val users = repository.getUserByUsername(main_dokter.activeUser!!)
            binding.NamaTVDokter.text = "Dr. ${users.fullname}"
            binding.specialistTvDokter.text = users.specialist
            binding.lamapraktikTvDokter.text = "${users.lama_praktik} years experiences"
        }

        binding.newArticleIV.setOnClickListener {
            findNavController().navigate(
                HomeDocterFragmentDirections
                    .actionGlobalBuatArtikel()
            )
        }

        binding.myreview.setOnClickListener {

        }

        binding.mychat.setOnClickListener {
            findNavController().navigate(
                HomeDocterFragmentDirections
                    .actionGlobalFragmentUserListchat()
            )
        }
    }
}