package com.example.amazonbooks.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.amazonbooks.data.BookRepo

class MainViewModel(private val repo: BookRepo): ViewModel() {

    val books = liveData {
        emit(repo.getBooks())
    }
}
