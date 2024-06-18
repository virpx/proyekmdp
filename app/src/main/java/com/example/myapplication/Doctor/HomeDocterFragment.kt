package com.example.myapplication.Doctor

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Database.MockDB
import com.example.myapplication.MainActivity
import com.example.myapplication.databinding.FragmentHomeDocterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            val averageRating = repository.getAverageRating(MockDB.usernamelogin)
            //the coroutine scope ioScope is using Dispatchers.IO, which is not the main thread. UI updates must be performed on the main thread.
            withContext(Dispatchers.Main) {
                binding.NamaTVDokter.text = "Dr. ${users.fullname}"
                binding.specialistTvDokter.text = users.specialist
                binding.lamapraktikTvDokter.text = "${users.lama_praktik} years experiences"

                val rating = averageRating["averageRating"]!!.toFloat() as? Float ?: 0.0f
                binding.ratingBarHomeDokter.rating = rating

                if (users?.foto_profile != "") {
                    val base64Image = users?.foto_profile ?: ""
                    val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                    binding.imageView17.setImageBitmap(bitmap)
                }
            }
        }

        binding.newArticleIV.setOnClickListener {
            findNavController().navigate(
                HomeDocterFragmentDirections
                    .actionGlobalBuatArtikel()
            )
        }

        binding.myreview.setOnClickListener {
            findNavController().navigate(
                HomeDocterFragmentDirections
                    .actionGlobalHistoryReviewDokterFragment()
            )
        }

        binding.mychat.setOnClickListener {
            findNavController().navigate(
                HomeDocterFragmentDirections
                    .actionGlobalFragmentUserListchat()
            )
        }

        binding.btnLogoutDokter.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}
