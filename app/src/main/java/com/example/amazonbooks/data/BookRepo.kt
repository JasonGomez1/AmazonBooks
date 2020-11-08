package com.example.amazonbooks.data

import com.example.amazonbooks.data.remote.Book
import kotlinx.coroutines.flow.Flow

interface BookRepo {
    fun getBooks(): Flow<List<Book>>
}