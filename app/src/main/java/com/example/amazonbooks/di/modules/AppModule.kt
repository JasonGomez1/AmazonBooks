package com.example.amazonbooks.di.modules

import android.content.Context
import androidx.room.Room
import com.example.amazonbooks.data.local.repo.BookRepo
import com.example.amazonbooks.data.local.repo.BookRepoImpl
import com.example.amazonbooks.data.local.db.BookDatabase
import com.example.amazonbooks.data.remote.ApiService
import com.example.amazonbooks.utils.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Making every function static makes it so that we don't have to create an instance of AppModule.
 * Since Dagger 2.26 we don't need to annotate companion objects with @Module or @JvmStatic.
 */
@Module
abstract class AppModule {
    @Binds abstract fun provideBookRepo(bookRepoImpl: BookRepoImpl): BookRepo

    companion object {
        @Provides
        @Singleton
        fun provideRemoteService(): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build().create(ApiService::class.java)
        }

        @Provides
        @Singleton
        fun provideBookDatabase(context: Context) =
            Room.databaseBuilder(context, BookDatabase::class.java, "book-database").build()
    }
}
