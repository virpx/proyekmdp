package com.example.myapplication.Doctor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentBuatArtikelBinding
import com.example.myapplication.main_dokter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

data class Artikel (var judul:String, var penulis:String, var isi:String, var view:Int, var
image:String)


class buatArtikel : Fragment() {
    private lateinit var binding: FragmentBuatArtikelBinding
    private val repository = main_dokter.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using DataBindingUtil
        binding = FragmentBuatArtikelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var penulis = main_dokter.activeUser!!
        var judul = binding.judulartikelET.text.toString()
        var isi = binding.isiartikelET.text.toString()
        var view = 0

        binding.btnUploadBanner.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                openImagePicker()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION_CODE
                )
            }
        }

        binding.btnPublishArtikel.setOnClickListener {
            val judul = binding.judulartikelET.text.toString().trim()
            val isi = binding.isiartikelET.text.toString().trim()

            if (judul.isEmpty()) {
                binding.judulartikelET.error = "The article title must be filled in"
                return@setOnClickListener
            }

            if (isi.isEmpty()) {
                binding.isiartikelET.error = "The contents of the article must be filled in"
                return@setOnClickListener
            }

            if (binding.previewImage.drawable == null) {
                Toast.makeText(
                    requireContext(),
                    "Please upload a banner for the article",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val drawable = binding.previewImage.drawable as BitmapDrawable
            val bitmap = drawable.bitmap

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val imageBytes = outputStream.toByteArray()
            val base64String = Base64.encodeToString(imageBytes, Base64.DEFAULT)

            ioScope.launch {
                val dataartikel = Artikel(judul,penulis,isi,view,base64String)
                repository.uploadArtikel(dataartikel)

            }
            Toast.makeText(requireContext(), "New Article Published", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, main_dokter::class.java)
            intent.putExtra("username", penulis)
            startActivity(intent)
            activity?.finish()
        }

    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openImagePicker()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private val getContentLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.previewImage.setImageURI(uri)
            }
        }

    private fun openImagePicker() {
        getContentLauncher.launch("image/*")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val REQUEST_PERMISSION_CODE = 100
    }

}