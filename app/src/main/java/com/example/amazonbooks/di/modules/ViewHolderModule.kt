package com.example.amazonbooks.di.modules

import androidx.lifecycle.*
import com.example.amazonbooks.data.local.repo.BookRepo
import com.example.amazonbooks.di.ViewHolderScope
import com.example.amazonbooks.ui.BookItemViewModel
import com.example.amazonbooks.ui.MainViewModel
import com.example.amazonbooks.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
abstract class ViewHolderModule {
    companion object {
        @Provides
        @ViewHolderScope
        fun provideLifecycle(owner: LifecycleOwner): LifecycleRegistry = LifecycleRegistry(owner)

        @Provides
        @Reusable
        fun provideBookVM(store: ViewModelStore, bookRepo: BookRepo): BookItemViewModel =
            ViewModelProvider(store, ViewModelProviderFactory(BookItemViewModel::class) {
                BookItemViewModel(bookRepo)
            }).get(BookItemViewModel::class.java)

        @Provides
        fun provideViewModelStore(mainViewModel: MainViewModel, id: Int): ViewModelStore =
            mainViewModel.getViewHolderStore(id) ?: ViewModelStore()
    }
}
