package com.example.bookbuddy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookbuddy.database.dao.AuthorDao
import com.example.bookbuddy.database.dao.BookDao
import com.example.bookbuddy.database.dao.GenreDao
import com.example.bookbuddy.database.entity.Genre

@Database(
    entities = [Genre::class], // add the rest here
    version = 1
)
abstract class BookDatabase: RoomDatabase() {

    abstract val genre: GenreDao
//    abstract val book: BookDao
//    abstract val author: AuthorDao
}