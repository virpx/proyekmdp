package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val repository = MainActivity.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using DataBindingUtil
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionGlobalUserFragment())
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.usernameET.text.toString()
            val password = binding.passwordET.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                showToastOnMainThread("Username and password must not be empty")
            } else {
                ioScope.launch {
                    try {
                        val loginRequest = mapOf("username" to username, "password" to password)
                        val response = repository.loginUser(loginRequest)
                        val msg = response["msg"]

                        if (msg == "Login successful") {
                            val role = response["role"]
                            if (role == "patient") {
                                showToastOnMainThread("Logged in as patient")
                            } else if (role == "doctor") {
                                showToastOnMainThread("Logged in as doctor")
                            }
                        } else {
                            showToastOnMainThread(msg!!)
                        }
                    } catch (e: Exception) {
                        showToastOnMainThread("Login failed: ${e.message}")
                    }
                }
            }
        }

    }

    private fun showToastOnMainThread(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}
