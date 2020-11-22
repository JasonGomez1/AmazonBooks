package com.example.amazonbooks.ui

import android.util.Log
import com.bumptech.glide.Glide
import com.example.amazonbooks.data.remote.Book
import com.example.amazonbooks.databinding.BookItemBinding
import com.example.amazonbooks.di.components.ViewHolderComponent
import com.example.amazonbooks.ui.base.BaseItemViewHolder

class BookViewHolder(
    private val binding: BookItemBinding
) : BaseItemViewHolder<Book, BookItemViewModel>(binding) {
    override fun setupObservers() {
        viewModel.data.observe(this) { book ->
            Log.d("BookViewHolder", "In observe with book: $book")
            book?.let {
                binding.apply {
                    tvTitle.text = book.title
                    tvAuthor.text = book.author
                    book.imageURL?.let {
                        Glide.with(binding.root.context)
                            .load(book.imageURL)
                            .fitCenter()
                            .into(ivBook)
                    }
                }
            }
        }
    }

    override fun injectDependencies(component: ViewHolderComponent) = component.inject(this)
}
