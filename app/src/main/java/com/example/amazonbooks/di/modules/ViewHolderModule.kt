package com.example.amazonbooks.di.modules

import androidx.lifecycle.*
import com.example.amazonbooks.data.BookRepo
import com.example.amazonbooks.ui.BookItemViewModel
import com.example.amazonbooks.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
abstract class ViewHolderModule {
    companion object {
        @Provides
        fun provideLifecycle(owner: LifecycleOwner): LifecycleRegistry = LifecycleRegistry(owner)

        @Provides
        fun provideBookVM(store: ViewModelStore, bookRepo: BookRepo): BookItemViewModel =
            ViewModelProvider(store, ViewModelProviderFactory(BookItemViewModel::class) {
                BookItemViewModel(bookRepo)
            }).get(BookItemViewModel::class.java)
    }
}
