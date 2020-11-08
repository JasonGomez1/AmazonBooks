package com.example.amazonbooks.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amazonbooks.data.Book
import com.example.amazonbooks.databinding.BookItemBinding

class BookAdapter : ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BookViewHolder(
            BookItemBinding
                .inflate(LayoutInflater
                    .from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

    class BookViewHolder(private val binding: BookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.apply {
                tvTitle.text = book.title
                tvAuthor.text = book.author
                book.imageURL?.let {
                    Glide.with(binding.root.context)
                        .load(book.imageURL)
                        .into(ivBook)
                }
            }
        }
    }
}
