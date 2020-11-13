package com.example.amazonbooks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.amazonbooks.data.BookRepo
import com.example.amazonbooks.data.remote.Book

class MainViewModel(private val repo: BookRepo) : ViewModel() {

    val books: LiveData<List<Book>>
        get() = repo
            .getBooks()
            .asLiveData()
}
