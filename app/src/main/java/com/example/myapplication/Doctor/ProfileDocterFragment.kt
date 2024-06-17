package com.example.myapplication.Doctor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.myapplication.Database.User
import com.example.myapplication.Doctor.main_dokter
import com.example.myapplication.databinding.FragmentProfileDocterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class ProfileDocterFragment : Fragment() {
    private lateinit var binding: FragmentProfileDocterBinding
    private val repository = main_dokter.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using DataBindingUtil
        binding = FragmentProfileDocterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var tempuser: User? = null
        ioScope.launch {
            // Fetch user data asynchronously
            tempuser = repository.getUserByUsername(main_dokter.activeUser!!)

            // Update UI on the main thread after data is fetched
            withContext(Dispatchers.Main) {
                binding.EmailETProfile.setText(tempuser?.email ?: "")
                binding.lamapraktikETProfile.setText(tempuser?.lama_praktik.toString())
                binding.fullnameETProfile.setText(tempuser?.fullname ?: "")

                if (tempuser?.foto_profile != "") {
                    val base64Image = tempuser?.foto_profile ?: ""
                    val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                    binding.ImageViewProfileDokter.setImageBitmap(bitmap)
                }
            }
        }



        binding.ImageViewProfileDokter.setOnClickListener {
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

        binding.btnSaveProfile.setOnClickListener {
            if (binding.ImageViewProfileDokter.drawable == null) {
                Toast.makeText(
                    requireContext(),
                    "Please upload a profile picture",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val drawable = binding.ImageViewProfileDokter.drawable as BitmapDrawable
            val bitmap = drawable.bitmap

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val imageBytes = outputStream.toByteArray()
            val base64String = Base64.encodeToString(imageBytes, Base64.DEFAULT)

            ioScope.launch {
                // Ensure tempuser is fetched and not null
                val currentUser = tempuser ?: return@launch

                // Update user properties
                currentUser.foto_profile = base64String
                currentUser.email = binding.EmailETProfile.text.toString()
                currentUser.fullname = binding.fullnameETProfile.text.toString()

                // Call repository to update user
                repository.updateDokterProfile(currentUser)

                // Display toast on the main thread
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Profile Updated", Toast.LENGTH_SHORT).show()
                }

                // Navigate to main_dokter activity
                val intent = Intent(context, main_dokter::class.java)
                intent.putExtra("username", main_dokter.activeUser)
                startActivity(intent)
                activity?.finish()
            }
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
                binding.ImageViewProfileDokter.setImageURI(uri)
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