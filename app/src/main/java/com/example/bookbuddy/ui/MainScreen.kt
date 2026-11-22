package com.example.bookbuddy.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookbuddy.database.entity.Author
import com.example.bookbuddy.database.entity.Book
import com.example.bookbuddy.database.entity.BookStatus
import com.example.bookbuddy.database.entity.Genre
import com.example.bookbuddy.viewmodel.BookViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Books : Screen("books", "Books", Icons.Default.Book)
    object Authors : Screen("authors", "Authors", Icons.Default.Person)
    object Genres : Screen("genres", "Genres", Icons.Default.Category)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: BookViewModel = viewModel()) {
    var selectedScreen by remember { mutableStateOf<Screen>(Screen.Books) }
    val books by viewModel.allBooks.collectAsState()
    val authors by viewModel.allAuthors.collectAsState()
    val genres by viewModel.allGenres.collectAsState()

    var selectedStatusFilter by remember { mutableStateOf<BookStatus?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BookBuddy - ${selectedScreen.title}") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                val items = listOf(Screen.Books, Screen.Authors, Screen.Genres)
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = selectedScreen == screen,
                        onClick = { selectedScreen = screen }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedScreen) {
                Screen.Books -> BooksScreen(
                    books = if (selectedStatusFilter == null) books else books.filter { it.status == selectedStatusFilter },
                    selectedStatusFilter = selectedStatusFilter,
                    onStatusFilterChanged = { selectedStatusFilter = it },
                    onAddSampleBook = {
                        viewModel.insertBook(
                            Book(
                                id = 0,
                                title = "Sample Book ${System.currentTimeMillis() % 100}",
                                author = "Unknown Author",
                                genre = -1,
                                rating = 4.5,
                                review = "Great read!",
                                status = BookStatus.READING,
                                date = System.currentTimeMillis() / (1000 * 60 * 60 * 24),
                                favourite = true,
                                imgpath = ""
                            )
                        )
                    },
                    onDeleteBook = { viewModel.deleteBook(it) }
                )
                Screen.Authors -> AuthorsScreen(
                    authors = authors,
                    onAddSampleAuthor = {
                        viewModel.insertAuthor(
                            Author(
                                name = "Author ${System.currentTimeMillis() % 100}",
                                genre = -1,
                                magnum = 0
                            )
                        )
                    }
                )
                Screen.Genres -> GenresScreen(
                    genres = genres,
                    onAddSampleGenre = {
                        val subId = (System.currentTimeMillis() % 1000).toInt()
                        viewModel.insertGenre(
                            Genre(
                                subId = subId,
                                mainId = 0,
                                name = "Genre $subId"
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun BooksScreen(
    books: List<Book>,
    selectedStatusFilter: BookStatus?,
    onStatusFilterChanged: (BookStatus?) -> Unit,
    onAddSampleBook: () -> Unit,
    onDeleteBook: (Book) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Books (${books.size})", style = MaterialTheme.typography.titleMedium)
            Button(onClick = onAddSampleBook) {
                Text("Add Book")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Status Filter Tabs
        ScrollableTabRow(
            selectedTabIndex = if (selectedStatusFilter == null) 0 else BookStatus.entries.indexOf(selectedStatusFilter) + 1,
            edgePadding = 0.dp
        ) {
            Tab(
                selected = selectedStatusFilter == null,
                onClick = { onStatusFilterChanged(null) },
                text = { Text("All") }
            )
            BookStatus.entries.forEach { status ->
                Tab(
                    selected = selectedStatusFilter == status,
                    onClick = { onStatusFilterChanged(status) },
                    text = { Text(status.value.uppercase()) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(books) { book ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(book.title, style = MaterialTheme.typography.titleMedium)
                            Text(book.status.value.uppercase(), style = MaterialTheme.typography.labelMedium)
                        }
                        Text("Author: ${book.author}", style = MaterialTheme.typography.bodyMedium)
                        if (book.rating != null) {
                            Text("Rating: ${book.rating} / 5", style = MaterialTheme.typography.bodySmall)
                        }
                        TextButton(onClick = { onDeleteBook(book) }) {
                            Text("Delete", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AuthorsScreen(authors: List<Author>, onAddSampleAuthor: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Authors (${authors.size})", style = MaterialTheme.typography.titleMedium)
            Button(onClick = onAddSampleAuthor) {
                Text("Add Author")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(authors) { author ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(author.name, style = MaterialTheme.typography.titleMedium)
                        Text("Magnum ID: ${author.magnum}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
fun GenresScreen(genres: List<Genre>, onAddSampleGenre: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Genres (${genres.size})", style = MaterialTheme.typography.titleMedium)
            Button(onClick = onAddSampleGenre) {
                Text("Add Genre")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(genres) { genre ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(genre.name, style = MaterialTheme.typography.titleMedium)
                        Text("Sub ID: ${genre.subId} | Main ID: ${genre.mainId}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
