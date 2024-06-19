package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Classuniversal_foodtrack
import com.example.myapplication.Database.MockDB
import com.example.myapplication.main_user
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodTrackViewModel : ViewModel() {

    private val repository = main_user.Repository
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _foodList = MutableLiveData<List<Classuniversal_foodtrack>>()
    val foodList: LiveData<List<Classuniversal_foodtrack>> get() = _foodList

    fun fetchFoodList() {
        ioScope.launch {
            val fetchedList = repository.getlistfoodtrack(MockDB.usernamelogin)
            withContext(Dispatchers.Main) {
                _foodList.value = fetchedList.toList()
            }
        }
    }
}
