package com.example.bookbuddy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bookbuddy.database.entity.Book
import com.example.bookbuddy.database.entity.BookStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book): Long

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBookById(id: Int): Flow<Book?>

    @Query("SELECT * FROM books WHERE author = :authorName")
    fun getBooksByAuthor(authorName: String): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE genre = :genreId")
    fun getBooksByGenre(genreId: Int): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE status = :status")
    fun getBooksByStatus(status: BookStatus): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE favourite = 1")
    fun getFavouriteBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books ORDER BY date DESC")
    fun getBooksSortedByDate(): Flow<List<Book>>

    @Query("SELECT * FROM books ORDER BY rating DESC")
    fun getBooksSortedByRating(): Flow<List<Book>>
}
