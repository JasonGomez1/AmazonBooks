package com.example.amazonbooks.ui

import com.example.amazonbooks.data.local.repo.BookRepo
import com.example.amazonbooks.data.remote.BookDataImpl
import com.example.amazonbooks.ui.base.BaseItemViewModel

class BookItemViewModel(private val repo: BookRepo): BaseItemViewModel<BookDataImpl>()
