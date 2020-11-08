package com.example.amazonbooks.di.modules

import com.example.amazonbooks.data.BookRepo
import com.example.amazonbooks.data.BookRepoImpl
import com.example.amazonbooks.data.remote.ApiService
import com.example.amazonbooks.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRemoteService(): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookRepo(service: ApiService): BookRepo =
        BookRepoImpl(service)
}
