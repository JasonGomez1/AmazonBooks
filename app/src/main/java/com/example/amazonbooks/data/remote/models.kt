package com.example.amazonbooks.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Book(val title: String, val author: String? = null, val imageURL: String? = null)
