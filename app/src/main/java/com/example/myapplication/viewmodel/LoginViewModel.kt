package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Database.LoginDB
import com.example.myapplication.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    sealed class LoginResult {
        data class Success(val message: String, val role: String) : LoginResult()
        data class Error(val errorMessage: String) : LoginResult()
    }

    fun loginUser(username: String, password: String, onResult: (LoginResult) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loginRequest = mapOf("username" to username, "password" to password)
                val response = MainActivity.Repository.loginUser(loginRequest)
                val msg = response["msg"]

                if (msg == "Login successful") {
                    val role = response["role"]
                    if (role == "patient" || role == "doctor") {
                        MainActivity.Repository.deletelogindb()
                        MainActivity.Repository.inserlogindb(LoginDB(username,password))
                        onResult(LoginResult.Success("Logged in as $role", role))
                    } else {
                        onResult(LoginResult.Error("Invalid role"))
                    }
                } else {
                    onResult(LoginResult.Error(msg ?: "Login failed"))
                }
            } catch (e: Exception) {
                val errorMessage = "Login failed: ${e.message ?: "Unknown error"}"
                onResult(LoginResult.Error(errorMessage))
            }
        }
    }
}
