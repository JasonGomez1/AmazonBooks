package com.example.amazonbooks.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.amazonbooks.data.BookRepo
import com.example.amazonbooks.ui.MainViewModel
import com.example.amazonbooks.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
abstract class ActivityModule {
    companion object {
        @Provides
        fun provideMainViewModelFactory(bookRepo: BookRepo): ViewModelProvider.Factory =
            ViewModelProviderFactory(MainViewModel::class) {
                MainViewModel(bookRepo)
            }
    }
}
