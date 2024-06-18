package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Doctor.main_dokter
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionGlobalUserFragment())
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.usernameET.text.toString()
            val password = binding.passwordET.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                showToast("Username and password must not be empty")
            } else {
                viewModel.loginUser(username, password) { result ->
                    when (result) {
                        is LoginViewModel.LoginResult.Success -> {
                            showToast(result.message)
                            navigateToMainScreen(result.role, username)
                        }
                        is LoginViewModel.LoginResult.Error -> {
                            showToast(result.errorMessage)
                        }
                    }
                }
            }
        }

        binding.forgotpwTv.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionGlobalForgotPasswordFragment())
        }
    }

    private fun navigateToMainScreen(role: String, username: String) {
        val intent = when (role) {
            "patient" -> Intent(context, main_user::class.java)
            "doctor" -> Intent(context, main_dokter::class.java)
            else -> null
        }
        intent?.putExtra("username", username)
        startActivity(intent!!)
        activity?.finish()
    }

    private fun showToast(message: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}
