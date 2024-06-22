package com.example.myapplication.User

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.example.myapplication.Database.User
import com.example.myapplication.MainActivity
import com.example.myapplication.databinding.FragmentMyProfileBinding
import com.example.myapplication.main_user
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class MyProfileFragment : Fragment() {
    private lateinit var binding: FragmentMyProfileBinding
    private val repository = main_user.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutIV.setOnClickListener {
            ioScope.launch {
                repository.deletelogindb()
                requireActivity().runOnUiThread {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
        }
        binding.logoutTvUser.setOnClickListener {
            ioScope.launch {
                repository.deletelogindb()
                requireActivity().runOnUiThread {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
        }

        var tempuser: User? = null
        ioScope.launch {
            // Fetch user data asynchronously
            tempuser = repository.getUserByUsername(main_user.activeUser!!)

            // Update UI on the main thread after data is fetched
            withContext(Dispatchers.Main) {
                binding.emailETUser.setText(tempuser?.email ?: "")
                binding.fullnameETUser.setText(tempuser?.fullname ?: "")

                if (tempuser?.foto_profile != "") {
                    val base64Image = tempuser?.foto_profile ?: ""
                    val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                    binding.profileUserIV.setImageBitmap(bitmap)
                }
            }
        }

        binding.profileUserIV.setOnClickListener {
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

        binding.btnSaveProfileUser.setOnClickListener {
            if (binding.profileUserIV.drawable == null) {
                Toast.makeText(
                    requireContext(),
                    "Please upload a profile picture",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val drawable = binding.profileUserIV.drawable as BitmapDrawable
            val bitmap = drawable.bitmap

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val imageBytes = outputStream.toByteArray()
            val base64String = Base64.encodeToString(imageBytes, Base64.DEFAULT)

            ioScope.launch {
                val currentUser = tempuser ?: return@launch

                currentUser.foto_profile = base64String
                currentUser.email = binding.emailETUser.text.toString()
                currentUser.fullname = binding.fullnameETUser.text.toString()

                repository.updateUserProfile(currentUser)

                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Profile Updated", Toast.LENGTH_SHORT).show()
                }

                val intent = Intent(context, main_user::class.java)
                intent.putExtra("username", main_user.activeUser)
                startActivity(intent)
                activity?.finish()
            }
        }

        binding.historyIV.setOnClickListener {
            findNavController().navigate(MyProfileFragmentDirections.actionGlobalHistoryMakananFragment())
        }

        binding.historyTvUser.setOnClickListener {
            findNavController().navigate(MyProfileFragmentDirections.actionGlobalHistoryMakananFragment())
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
                binding.profileUserIV.setImageURI(uri)
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
