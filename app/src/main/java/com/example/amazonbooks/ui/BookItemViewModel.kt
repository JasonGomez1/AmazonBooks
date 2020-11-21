package com.example.amazonbooks.ui

import com.example.amazonbooks.data.BookRepo
import com.example.amazonbooks.data.remote.Book
import com.example.amazonbooks.ui.base.BaseItemViewModel

class BookItemViewModel(private val repo: BookRepo): BaseItemViewModel<Book>()
