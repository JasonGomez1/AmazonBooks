package com.example.amazonbooks.ui

import com.example.amazonbooks.data.remote.Book
import com.example.amazonbooks.ui.base.BaseDiffUtil

class BookDiffUtil: BaseDiffUtil<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book) =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: Book, newItem: Book) =
        oldItem == newItem
}
