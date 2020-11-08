package com.example.amazonbooks.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.amazonbooks.data.BookRepo

class MainViewModel(private val repo: BookRepo) : ViewModel() {

    val books = repo.getBooks().asLiveData()
}
