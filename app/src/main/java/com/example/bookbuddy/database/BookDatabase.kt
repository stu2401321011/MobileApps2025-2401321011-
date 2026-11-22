package com.example.bookbuddy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bookbuddy.database.dao.AuthorDao
import com.example.bookbuddy.database.dao.BookDao
import com.example.bookbuddy.database.dao.GenreDao
import com.example.bookbuddy.database.entity.Author
import com.example.bookbuddy.database.entity.Book
import com.example.bookbuddy.database.entity.BookStatusConverter
import com.example.bookbuddy.database.entity.Genre

@Database(
    entities = [Genre::class, Author::class, Book::class],
    version = 1
)
@TypeConverters(BookStatusConverter::class)
abstract class BookDatabase: RoomDatabase() {

    abstract val genre: GenreDao
    abstract val book: BookDao
    abstract val author: AuthorDao
}
