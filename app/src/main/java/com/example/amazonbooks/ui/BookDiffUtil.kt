package com.example.amazonbooks.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.amazonbooks.data.Book

class BookDiffUtil: DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book) =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: Book, newItem: Book) =
        oldItem == newItem
}
