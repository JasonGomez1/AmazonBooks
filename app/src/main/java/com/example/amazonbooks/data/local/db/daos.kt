package com.example.amazonbooks.data.local.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getBooksFlow(): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(books: List<BookEntity>)

    @Delete
    suspend fun deleteBook(book: BookEntity)
}
