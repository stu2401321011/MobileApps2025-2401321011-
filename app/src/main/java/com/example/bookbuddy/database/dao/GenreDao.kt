package com.example.bookbuddy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bookbuddy.database.entity.Genre
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Insert  // create new genre
    suspend fun insertGenre(genre: Genre); // suspend for async

    //Query to read all genres
    @Query("SELECT * FROM genres") // read all genres
    fun getAllGenres(): Flow<List<Genre>>

    //Query select main genres
    @Query("SELECT * FROM genres WHERE genres.mainId = 0")
    fun getMainGenres(): Flow<List<Genre>>

    //Query get specific genre
    @Query("SELECT * FROM genres WHERE subId = :subId")
    fun getGenreBySubId(subId: Int): Flow<Genre?>

    @Update
    suspend fun updateGenre(genre: Genre)

    @Delete
    suspend fun deleteGenre(genre: Genre);
}