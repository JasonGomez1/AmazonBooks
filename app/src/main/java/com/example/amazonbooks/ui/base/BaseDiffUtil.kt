package com.example.amazonbooks.ui.base

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffUtil<T: Data>: DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: T, newItem: T) =
        oldItem == newItem
}