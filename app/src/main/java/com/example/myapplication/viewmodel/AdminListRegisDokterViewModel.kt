package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Admin.Admin_class_list_regis_dokter
import com.example.myapplication.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminListRegisDokterViewModel : ViewModel() {
    private val repository = MainActivity.Repository
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _doctors = MutableLiveData<List<Admin_class_list_regis_dokter>>()
    val doctors: LiveData<List<Admin_class_list_regis_dokter>> get() = _doctors

    fun fetchDoctors() {
        ioScope.launch {
            val doctorsList = repository.admingetregisdokter()
            _doctors.postValue(doctorsList)
        }
    }

    fun deleteDoctor(username: String, onComplete: () -> Unit) {
        ioScope.launch {
            repository.admindeletedokterregis(username)
            onComplete()
        }
    }

    fun acceptDoctor(username: String, onComplete: () -> Unit) {
        ioScope.launch {
            repository.adminaccdokterregis(username)
            onComplete()
        }
    }
}
