package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Admin_class_review_user
import com.example.myapplication.Database.User
import com.example.myapplication.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailUserAdminViewModel : ViewModel() {
    private val repository = MainActivity.Repository
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> get() = _userData

    private val _reviews = MutableLiveData<List<Admin_class_review_user>>()
    val reviews: LiveData<List<Admin_class_review_user>> get() = _reviews

    fun fetchUserDetails(username: String) {
        ioScope.launch {
            val user = repository.getUserByUsername(username)
            _userData.postValue(user)
        }
    }

    fun fetchUserReviews(username: String) {
        ioScope.launch {
            val reviewsList = repository.admingetuserreview(username)
            _reviews.postValue(reviewsList)
        }
    }

    fun deleteUser(username: String, onSuccess: () -> Unit) {
        ioScope.launch {
            repository.adminhapususer(username)
            onSuccess()
        }
    }
}