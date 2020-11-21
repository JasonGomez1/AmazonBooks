package com.example.amazonbooks.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.example.amazonbooks.data.remote.Book
import com.example.amazonbooks.databinding.BookItemBinding
import com.example.amazonbooks.ui.base.BaseAdapter

class BookAdapter(
    lifecycle: Lifecycle
) : BaseAdapter<Book, BookViewHolder>(lifecycle, BookDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BookViewHolder(
            BookItemBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context), parent, false
                )
        )
}
