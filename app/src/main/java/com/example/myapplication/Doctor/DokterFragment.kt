package com.example.myapplication.Doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Database.User
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.User.UserFragmentDirections
import com.example.myapplication.databinding.FragmentDokterBinding
import com.example.myapplication.databinding.FragmentUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DokterFragment : Fragment() {
    private lateinit var binding: FragmentDokterBinding
    private val repository = MainActivity.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDokterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var arrUser: ArrayList<User>? = null

        ioScope.launch {
            var semuauser = repository.getAllUser()
            arrUser?.addAll(semuauser)
        }

        val specialties = arrayListOf(
            "General Practitioner",
            "Cardiologist",
            "Dermatologist",
            "Neurologist",
            "Orthopedic Surgeon",
            "Pediatrician",
            "Pulmonologist",
            "Psychiatrist",
            "Radiologist"
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, specialties)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.specialistSpinner.adapter = adapter

        binding.btnToRegisterU.setOnClickListener {
            findNavController().navigate(DokterFragmentDirections.actionGlobalDokterFragment())
        }

        binding.btnRegisterD.setOnClickListener {
            if (validateInputs()) {
                val username = binding.usernameETD.text.toString().trim()
                val email = binding.emailETD.text.toString().trim()
                val fullName = binding.fullnameETD.text.toString().trim()
                val password = binding.passwordETD.text.toString().trim()
                val gender = if (binding.lakiRB2.isChecked) "Male" else "Female"
                val specialist = binding.specialistSpinner.selectedItem.toString()

                if (arrUser?.any { it.username == username } == true) {
                    showToastOnMainThread("Username already exists")
                    return@setOnClickListener
                }
                else if (arrUser?.any { it.email== email } == true) {
                    showToastOnMainThread("Email already exists")
                    return@setOnClickListener
                }

                ioScope.launch {
                    val newUser = User(
                        username = username,
                        email = email,
                        fullname = fullName,
                        password = password,
                        gender = gender,
                        specialist = specialist
                    )
                    repository.createUser(newUser)
                    showToastOnMainThread("Registration Successful")
                }

                findNavController().navigate(DokterFragmentDirections.actionGlobalLoginFragment())
            }
        }
    }

    private fun validateInputs(): Boolean {
        val email = binding.emailETD.text.toString().trim()
        val username = binding.usernameETD.text.toString().trim()
        val fullName = binding.fullnameETD.text.toString().trim()
        val password = binding.passwordETD.text.toString().trim()
        val confirmPassword = binding.cpasswordETD.text.toString().trim()
        val isMale = binding.lakiRB2.isChecked
        val isFemale = binding.perempuanRB2.isChecked

        if (username.isEmpty()) {
            binding.usernameETD.error = "Username is required"
            binding.usernameETD.requestFocus()
            return false
        }

        if (email.isEmpty()) {
            binding.emailETD.error = "Email is required"
            binding.emailETD.requestFocus()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailETD.error = "Enter a valid email"
            binding.emailETD.requestFocus()
            return false
        }

        if (fullName.isEmpty()) {
            binding.fullnameETD.error = "Full name is required"
            binding.fullnameETD.requestFocus()
            return false
        }
        if (password.isEmpty()) {
            binding.passwordETD.error = "Password is required"
            binding.passwordETD.requestFocus()
            return false
        }
        if (password.length < 6) {
            binding.passwordETD.error = "Password should be at least 6 characters long"
            binding.passwordETD.requestFocus()
            return false
        }
        if (confirmPassword.isEmpty()) {
            binding.cpasswordETD.error = "Confirm password is required"
            binding.cpasswordETD.requestFocus()
            return false
        }
        if (password != confirmPassword) {
            binding.cpasswordETD.error = "Passwords do not match"
            binding.cpasswordETD.requestFocus()
            return false
        }
        if (!isMale && !isFemale) {
            Toast.makeText(requireContext(), "Please select a gender", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun showToastOnMainThread(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}
