package com.example.amazonbooks.data.remote

import retrofit2.http.GET

interface ApiService {

    @GET("books.json")
    suspend fun getBooks(): List<Book>
}
