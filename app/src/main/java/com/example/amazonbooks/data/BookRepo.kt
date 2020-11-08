package com.example.amazonbooks.data

interface BookRepo {
    suspend fun getBooks(): List<Book>
}