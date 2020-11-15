package com.example.amazonbooks.data

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
            .map { entities ->
                if (entities.isEmpty() || entities.first().isGreaterThanQueryThreshold()) {
                    getAndStoreBooks()
                    emptyList()
                } else {
                    entities.map {
                        Book(title = it.title, author = it.author, imageURL = it.imageURL)
                    }
                }
            }
            .filterNot { books -> books.isEmpty() }
            .flowOn(Dispatchers.IO)

    private suspend fun getAndStoreBooks() =
        service.getBooks()
            .asFlow()
            .map {
                val timeStamp = TimeUnit.MILLISECONDS
                    .toMinutes(System.currentTimeMillis())
                    .toString()
                BookEntity(
                    imageURL = it.imageURL,
                    timeStamp = timeStamp,
                    title = it.title,
                    author = it.author
                )
            }
            .toList()
            .also { bookEntities ->
                db.bookDao()
                    .insertAllBooks(bookEntities)
            }
}
