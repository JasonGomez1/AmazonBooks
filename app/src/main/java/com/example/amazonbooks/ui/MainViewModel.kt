package com.example.amazonbooks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.asLiveData
import com.example.amazonbooks.data.local.repo.BookRepo
import com.example.amazonbooks.data.remote.BookDataImpl

class MainViewModel(private val repo: BookRepo) : ViewModel() {

    val books: LiveData<List<BookDataImpl>>
        get() = repo
            .getBooks()
            .asLiveData()

    private val viewHolderStores: MutableMap<Int, ViewModelStore> = mutableMapOf()

    fun setViewHolderStore(id: Int, store: ViewModelStore) {
        viewHolderStores[id] = store
    }

    fun getViewHolderStore(id: Int) = viewHolderStores[id]

    override fun onCleared() {
        viewHolderStores.forEach { (_, viewModelStore) ->
            viewModelStore.clear()
        }
        super.onCleared()
    }
}
