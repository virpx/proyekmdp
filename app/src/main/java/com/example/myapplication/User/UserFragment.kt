package com.example.myapplication.User

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Database.User
import com.example.myapplication.MainActivity
import com.example.myapplication.databinding.FragmentUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private val repository = MainActivity.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var arrUser: ArrayList<User>? = null

        ioScope.launch {
            var semuauser = repository.getAllUser()
            arrUser?.addAll(semuauser)
        }

        binding.btnToLogin.setOnClickListener {
            findNavController().navigate(UserFragmentDirections.actionGlobalLoginFragment())
        }

        binding.toregisterdoctor.setOnClickListener {
            findNavController().navigate(UserFragmentDirections.actionGlobalDokterFragment())
        }

        binding.btnRegisterU.setOnClickListener {
            if (validateInputs()) {
                val username = binding.usernameETU.text.toString().trim()
                val email = binding.emailETU.text.toString().trim()
                val fullName = binding.fullnameETU.text.toString().trim()
                val password = binding.passwordETU.text.toString().trim()
                val gender = if (binding.lakiRB.isChecked) "Male" else "Female"

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
                        specialist = ""
                    )
                    repository.createUser(newUser)
                    showToastOnMainThread("Registration Successful")
                }

                findNavController().navigate(UserFragmentDirections.actionGlobalLoginFragment())
            }
        }


    }

    private fun validateInputs(): Boolean {
        val email = binding.emailETU.text.toString().trim()
        val username = binding.usernameETU.text.toString().trim()
        val fullName = binding.fullnameETU.text.toString().trim()
        val password = binding.passwordETU.text.toString().trim()
        val confirmPassword = binding.cpasswordETU.text.toString().trim()
        val isMale = binding.lakiRB.isChecked
        val isFemale = binding.perempuanRB.isChecked

        if (username.isEmpty()) {
            binding.usernameETU.error = "Username is required"
            binding.usernameETU.requestFocus()
            return false
        }

        if (email.isEmpty()) {
            binding.emailETU.error = "Email is required"
            binding.emailETU.requestFocus()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailETU.error = "Enter a valid email"
            binding.emailETU.requestFocus()
            return false
        }

        if (fullName.isEmpty()) {
            binding.fullnameETU.error = "Full name is required"
            binding.fullnameETU.requestFocus()
            return false
        }
        if (password.isEmpty()) {
            binding.passwordETU.error = "Password is required"
            binding.passwordETU.requestFocus()
            return false
        }
        if (password.length < 6) {
            binding.passwordETU.error = "Password should be at least 6 characters long"
            binding.passwordETU.requestFocus()
            return false
        }
        if (confirmPassword.isEmpty()) {
            binding.cpasswordETU.error = "Confirm password is required"
            binding.cpasswordETU.requestFocus()
            return false
        }
        if (password != confirmPassword) {
            binding.cpasswordETU.error = "Passwords do not match"
            binding.cpasswordETU.requestFocus()
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
