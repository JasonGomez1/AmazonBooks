package com.example.amazonbooks.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val title: String,
    @ColumnInfo(name = "image_url") val imageURL: String?,
    @ColumnInfo(name = "time_stamp") val timeStamp: String,
    val author: String?
)