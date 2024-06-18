package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Admin_class_list_user
import com.example.myapplication.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminListUserViewModel : ViewModel() {
    private val repository = MainActivity.Repository
    private val ioScope = CoroutineScope(Dispatchers.IO)

    // LiveData to hold the list of users
    private val _userData = MutableLiveData<List<Admin_class_list_user>>()
    val userData: LiveData<List<Admin_class_list_user>>
        get() = _userData

    // Function to fetch user data from repository
    fun fetchData(pilih: Int) {
        ioScope.launch {
            val hasil = repository.admingetluser()
            val filteredUsers = hasil.filter { it.jenis == pilih }
            _userData.postValue(filteredUsers)
        }
    }
}
