package com.example.bookbuddy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookbuddy.database.DatabaseProvider
import com.example.bookbuddy.database.entity.Author
import com.example.bookbuddy.database.entity.Book
import com.example.bookbuddy.database.entity.Genre
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseProvider.getDatabase(application)
    private val bookDao = database.book
    private val authorDao = database.author
    private val genreDao = database.genre

    val allBooks: StateFlow<List<Book>> = bookDao.getAllBooks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allAuthors: StateFlow<List<Author>> = authorDao.getAllAuthors()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allGenres: StateFlow<List<Genre>> = genreDao.getAllGenres()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertBook(book: Book) {
        viewModelScope.launch {
            bookDao.insertBook(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            bookDao.deleteBook(book)
        }
    }

    fun insertAuthor(author: Author) {
        viewModelScope.launch {
            authorDao.insertAuthor(author)
        }
    }

    fun insertGenre(genre: Genre) {
        viewModelScope.launch {
            genreDao.insertGenre(genre)
        }
    }
}
