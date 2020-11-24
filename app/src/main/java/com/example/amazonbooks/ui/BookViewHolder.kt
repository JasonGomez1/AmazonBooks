package com.example.amazonbooks.ui

import com.bumptech.glide.Glide
import com.example.amazonbooks.data.remote.BookDataImpl
import com.example.amazonbooks.databinding.BookItemBinding
import com.example.amazonbooks.di.components.ViewHolderComponent
import com.example.amazonbooks.ui.base.BaseItemViewHolder

class BookViewHolder(
    private val binding: BookItemBinding
) : BaseItemViewHolder<BookDataImpl, BookItemViewModel>(binding) {
    override fun setupObservers() {
        viewModel.data.observe(this) { book ->
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
