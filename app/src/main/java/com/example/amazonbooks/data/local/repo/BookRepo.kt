package com.example.amazonbooks.data.local.repo

import com.example.amazonbooks.data.remote.BookDataImpl
import kotlinx.coroutines.flow.Flow

interface BookRepo {
    fun getBooks(): Flow<List<BookDataImpl>>
}