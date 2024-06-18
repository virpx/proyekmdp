package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentForgotPasswordBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

data class KirimOtp(val otp: String, var email: String)
data class changePw(val password: String)

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val repository = MainActivity.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    var otp = ""
    var email = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using DataBindingUtil
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextEmail.visibility = EditText.VISIBLE
        binding.codeOTPET.visibility = EditText.VISIBLE
        binding.btnSubmitOTP.visibility = Button.VISIBLE
        binding.btnOTP.visibility = Button.VISIBLE

        binding.btnChangePw.visibility = Button.INVISIBLE
        binding.confirmPwET.visibility = EditText.INVISIBLE
        binding.newPwET.visibility = EditText.INVISIBLE

        binding.btnOTP.setOnClickListener {
            email = binding.editTextEmail.text.toString()

            if (email.isEmpty()) {
                binding.editTextEmail.error = "Email is required"
                binding.editTextEmail.requestFocus()
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.editTextEmail.error = "Enter a valid email"
                binding.editTextEmail.requestFocus()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val otpLength = 6
                    otp = generateOtp(otpLength)
                    try {
                        repository.sendotp(KirimOtp(otp, email))
                        showToastOnMainThread("Code sent to $email")
                    } catch (e: Exception) {
                        showToastOnMainThread("Failed to send OTP")
                    }
                }
            }
        }

        binding.btnSubmitOTP.setOnClickListener {
            val codeotp = binding.codeOTPET.text.toString()
            if (codeotp.isEmpty()) {
                binding.codeOTPET.error = "OTP is required"
                binding.codeOTPET.requestFocus()
                return@setOnClickListener
            }

            if (codeotp == otp) {
                binding.editTextEmail.visibility = EditText.INVISIBLE
                binding.codeOTPET.visibility = EditText.INVISIBLE
                binding.btnSubmitOTP.visibility = Button.INVISIBLE
                binding.btnOTP.visibility = Button.INVISIBLE

                binding.btnChangePw.visibility = Button.VISIBLE
                binding.confirmPwET.visibility = EditText.VISIBLE
                binding.newPwET.visibility = EditText.VISIBLE
            }
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigate(ForgotPasswordFragmentDirections.actionGlobalLoginFragment())
        }

        binding.btnChangePw.setOnClickListener {
            val password = binding.newPwET.text.toString().trim()
            val confirmPassword = binding.confirmPwET.text.toString().trim()

            if (password.isEmpty()) {
                binding.newPwET.error = "Password is required"
                binding.newPwET.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 6) {
                binding.newPwET.error = "Password should be at least 6 characters long"
                binding.newPwET.requestFocus()
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                binding.confirmPwET.error = "Confirm password is required"
                binding.confirmPwET.requestFocus()
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                binding.confirmPwET.error = "Passwords do not match"
                binding.confirmPwET.requestFocus()
                return@setOnClickListener
            }

            ioScope.launch {
                var pw = changePw(password)
                repository.changePassword(email, pw)

                showToastOnMainThread("Password Changed")
            }

            findNavController().navigate(ForgotPasswordFragmentDirections.actionGlobalLoginFragment())
        }
    }

    fun generateOtp(length: Int): String {
        val otp = StringBuilder()
        for (i in 0 until length) {
            otp.append(Random.nextInt(0, 10))
        }
        return otp.toString()
    }

    private fun showToastOnMainThread(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}
