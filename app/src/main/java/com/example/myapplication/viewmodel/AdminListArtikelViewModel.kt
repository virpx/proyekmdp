package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Admin.Admin_class_list_artikel
import com.example.myapplication.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminListArtikelViewModel : ViewModel() {
    private val repository = MainActivity.Repository
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _articles = MutableLiveData<List<Admin_class_list_artikel>>()
    val articles: LiveData<List<Admin_class_list_artikel>> get() = _articles

    fun fetchArticles() {
        ioScope.launch {
            val articlesList = repository.admingetlartikel()
            _articles.postValue(articlesList)
        }
    }

    fun deleteArticle(id: Int, onComplete: () -> Unit) {
        ioScope.launch {
            repository.adminhapusartkel(id)
            onComplete()
        }
    }
}
