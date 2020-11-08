package com.example.amazonbooks.data

import com.example.amazonbooks.data.remote.ApiService

class BookRepoImpl(private val service: ApiService): BookRepo {

    override suspend fun getBooks() = service.getBooks()
}
