package com.example.amazonbooks.utils

import com.example.amazonbooks.data.local.db.BookEntity
import java.util.concurrent.TimeUnit

fun BookEntity.isGreaterThanQueryThreshold() =
    TimeUnit
        .MILLISECONDS
        .toMinutes(System.currentTimeMillis()) - timeStamp.toLong() > Constants.QUERY_THRESHOLD
