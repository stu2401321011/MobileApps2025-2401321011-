package com.example.bookbuddy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bookbuddy.database.entity.Author
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthor(author: Author)

    @Query("SELECT * FROM authors")
    fun getAllAuthors(): Flow<List<Author>>

    @Query("SELECT * FROM authors WHERE name = :name")
    fun getAuthorByName(name: String): Flow<Author?>

    @Update
    suspend fun updateAuthor(author: Author)

    @Delete
    suspend fun deleteAuthor(author: Author)
}
