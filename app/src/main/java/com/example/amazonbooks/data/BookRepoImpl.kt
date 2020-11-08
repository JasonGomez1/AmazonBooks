package com.example.amazonbooks.data

import android.util.Log
import com.example.amazonbooks.data.local.db.BookDatabase
import com.example.amazonbooks.data.local.db.BookEntity
import com.example.amazonbooks.data.remote.ApiService
import com.example.amazonbooks.data.remote.Book
import com.example.amazonbooks.utils.Constants.QUERY_THRESHOLD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
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
            .getBooks()
            .distinctUntilChanged()
            .transform { entities ->
                if (entities.isEmpty()) {
                    Log.d("BookRepoImpl", "Table is empty")
                    val books = service.getBooks()
                    val timeStamp = TimeUnit.MILLISECONDS
                        .toMinutes(System.currentTimeMillis()).toString()
                    db.bookDao()
                        .insertBook(books.map {
                            BookEntity(
                                imageURL = it.imageURL,
                                timeStamp = timeStamp,
                                title = it.title,
                                author = it.author
                            )
                        })
                    emit(books)
                } else {
                    if (TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())
                        - entities.first().timeStamp.toLong() > QUERY_THRESHOLD
                    ) {
                        Log.d("BookRepoImpl", "Getting books from service")
                        val books = service.getBooks()
                        val timeStamp = TimeUnit.MILLISECONDS
                            .toMinutes(System.currentTimeMillis()).toString()
                        db.bookDao()
                            .insertBook(books.map {
                                BookEntity(
                                    imageURL = it.imageURL,
                                    timeStamp = timeStamp,
                                    title = it.title,
                                    author = it.author
                                )
                            })
                        emit(books)
                    } else {
                        Log.d("BookRepoImpl", "Getting books from database")
                        emit(entities.map {
                            Book(title = it.title, author = it.author, imageURL = it.imageURL)
                        })
                    }
                }
            }.flowOn(Dispatchers.IO)
}
