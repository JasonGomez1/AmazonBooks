package com.example.amazonbooks.di.modules

import com.example.amazonbooks.data.BookRepo
import com.example.amazonbooks.data.BookRepoImpl
import dagger.Binds
import dagger.Module

@Module
abstract class LocalModule {
    @Binds
    abstract fun provideBookRepo(bookRepoImpl: BookRepoImpl): BookRepo
}
