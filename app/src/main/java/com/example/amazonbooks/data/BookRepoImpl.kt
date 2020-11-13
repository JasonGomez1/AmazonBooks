package com.example.amazonbooks.data

import android.util.Log
import com.example.amazonbooks.data.local.db.BookDatabase
import com.example.amazonbooks.data.local.db.BookEntity
import com.example.amazonbooks.data.remote.ApiService
import com.example.amazonbooks.data.remote.Book
import com.example.amazonbooks.utils.isGreaterThanQueryThreshold
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepoImpl @Inject constructor(
    private val service: ApiService,
    private val db: BookDatabase
) : BookRepo {

    override fun getBooks(): Flow<List<Book>> =
        db.bookDao()
            .getBooksFlow()
            .distinctUntilChanged()
            .mapLatest { entities ->
                Log.d("BookRepo", "In mapLatest ${entities.size}")
                if (entities.isEmpty() || entities.first().isGreaterThanQueryThreshold()) {
                    Log.d("BookRepo", "In make api call size: ${entities.size}")
                    getAndStoreBooks()
                } else {
                    Log.d("BookRepo", "In less than threshold")
                    entities.map {
                        Book(title = it.title, author = it.author, imageURL = it.imageURL)
                    }
                }
            }
            .flowOn(Dispatchers.IO)

    private suspend fun getAndStoreBooks(): List<Book> {
        val books = service.getBooks()
        val timeStamp = TimeUnit.MILLISECONDS
            .toMinutes(System.currentTimeMillis()).toString()
        Log.d("BookRepo", "GetAndStoreBooks size: ${books.size}")
        db.bookDao()
            .insertBook(books.map {
                BookEntity(
                    imageURL = it.imageURL,
                    timeStamp = timeStamp,
                    title = it.title,
                    author = it.author
                )
            })
        return books
    }
}
