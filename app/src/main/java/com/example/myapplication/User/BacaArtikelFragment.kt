package com.example.myapplication.User

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.Database.MockDB
import com.example.myapplication.databinding.FragmentBacaArtikelBinding
import com.example.myapplication.main_user
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class BacaArtikelFragment : Fragment() {
    private lateinit var binding: FragmentBacaArtikelBinding
    private val repository = main_user.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBacaArtikelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.judulArtikelTv.text = MockDB.activeArtikel.judul
        binding.penulisArtikelTv.text = "By: Dr. ${MockDB.activeArtikel.penulis}"
        binding.isiArtikelTv.text=MockDB.activeArtikel.isi
    }
}