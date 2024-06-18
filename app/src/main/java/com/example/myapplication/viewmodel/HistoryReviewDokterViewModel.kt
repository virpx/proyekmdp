package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Doctor.Review
import com.example.myapplication.Doctor.main_dokter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryReviewDokterViewModel : ViewModel() {
    private val repository = main_dokter.Repository
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>> get() = _reviewList

    fun fetchReviewList(username: String) {
        ioScope.launch {
            val fetchedReviewList = repository.gethistoryreview(username)
            withContext(Dispatchers.Main) {
                _reviewList.value = fetchedReviewList.toList()
            }
        }
    }
}
