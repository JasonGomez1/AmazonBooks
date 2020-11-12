package com.example.amazonbooks.data

import android.util.Log
import com.example.amazonbooks.data.local.db.BookDatabase
import com.example.amazonbooks.data.local.db.BookEntity
import com.example.amazonbooks.data.remote.ApiService
import com.example.amazonbooks.data.remote.Book
import com.example.amazonbooks.utils.Constants.QUERY_THRESHOLD
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
            .getBooks()
            .map { entities ->
                Log.d("BookRepoImpl", "In map size: ${entities.size}")
                entities.map {
                    Book(title = it.title, author = it.author, imageURL = it.imageURL)
                }
            }
            .distinctUntilChanged()
            .transform { dbBooks ->
                Log.d("BookRepoImpl", "In transform")
                if (dbBooks.isEmpty()) {
                    Log.d("BookRepoImpl", "Table is empty")
                    val books = service.getBooks()
                    val timeStamp = TimeUnit.MILLISECONDS
                        .toMinutes(System.currentTimeMillis()).toString()
                    emit(books)
                    Log.d("BookRepoImpl", "Emitted books")
                    db.bookDao()
                        .insertBook(books.map {
                            BookEntity(
                                imageURL = it.imageURL,
                                timeStamp = timeStamp,
                                title = it.title,
                                author = it.author
                            )
                        })
                } else {
                    Log.d("BookRepoImpl", "Getting timestamp")
                    val oldTimeStamp = db.bookDao()
                        .getBooks()
                        .map { it.first() }
                        .single()
                        .timeStamp
                        .toLong()
                    Log.d("BookRepoImpl", "Got timestamp")
                    if (TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())
                        - oldTimeStamp > QUERY_THRESHOLD
                    ) {
                        Log.d("BookRepoImpl", "Getting books from service")
                        val books = service.getBooks()
                        val timeStamp = TimeUnit.MILLISECONDS
                            .toMinutes(System.currentTimeMillis()).toString()
                        emit(books)
                        Log.d("BookRepoImpl", "Emitted books")
                        db.bookDao()
                            .insertBook(books.map {
                                BookEntity(
                                    imageURL = it.imageURL,
                                    timeStamp = timeStamp,
                                    title = it.title,
                                    author = it.author
                                )
                            })
                    } else {
                        Log.d("BookRepoImpl", "Getting books from database")
                        emit(dbBooks.map {
                            Book(title = it.title, author = it.author, imageURL = it.imageURL)
                        })
                        Log.d("BookRepoImpl", "Emitted books")
                    }
                }
            }.catch { cause: Throwable? ->
                cause?.let {
                    Log.d("BookRepoImpl", "Error")
                }
            }.flowOn(Dispatchers.IO)
}
