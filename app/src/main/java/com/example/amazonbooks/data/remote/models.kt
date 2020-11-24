package com.example.amazonbooks.data.remote

import com.example.amazonbooks.ui.base.Data
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Book(val title: String, val author: String? = null, val imageURL: String? = null)

data class BookDataImpl(
    val title: String,
    val author: String? = null,
    val imageURL: String? = null,
    override val id: Int?
) : Data
