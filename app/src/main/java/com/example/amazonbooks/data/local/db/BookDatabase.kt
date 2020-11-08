package com.example.amazonbooks.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class], version = 1)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
}
