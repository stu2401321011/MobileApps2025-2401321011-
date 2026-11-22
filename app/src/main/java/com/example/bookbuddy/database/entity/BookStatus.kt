package com.example.bookbuddy.database.entity

import androidx.room.TypeConverter

enum class BookStatus(val value: String) {
    DOWNLOADED("downloaded"),
    TBR("tbr"),
    DNF("dnf"),
    READING("reading"),
    FINISHED("finished");

    companion object {
        fun fromValue(value: String): BookStatus {
            return entries.find { it.value.equals(value, ignoreCase = true) } ?: DOWNLOADED
        }
    }
}

class BookStatusConverter {
    @TypeConverter
    fun fromBookStatus(status: BookStatus): String {
        return status.value
    }

    @TypeConverter
    fun toBookStatus(value: String): BookStatus {
        return BookStatus.fromValue(value)
    }
}
