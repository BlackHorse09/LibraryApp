package com.sahil.bookapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sahil.bookapplication.repository.Repository
import com.sahil.bookapplication.room.BookEntity
import com.sahil.bookapplication.room.BooksDB
import com.sahil.bookapplication.screens.UpdateScreen
import com.sahil.bookapplication.ui.theme.BookApplicationTheme
import com.sahil.bookapplication.viewmodel.BooksViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookApplicationTheme {
                val context = LocalContext.current
                val booksDB = BooksDB.getInstance(context)
                val repository = Repository(booksDb = booksDB)
                val viewModel = BooksViewModel(repository = repository)

                //Navigation
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "MainScreen") {
                    composable("MainScreen") {
                        MainScreen(viewModel = viewModel, navController)
                    }

                    composable("UpdateScreen/{bookId}") {
                        UpdateScreen(viewModel = viewModel, bookID = it.arguments?.getString("bookId"))
                    }
                }

                //MainScreen(viewModel)
            }
        }
    }
}


@Composable
fun MainScreen(viewModel: BooksViewModel, navController: NavHostController) {
    var inputTitle by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .padding(24.dp)
        .fillMaxSize()) {
        OutlinedTextField(value = inputTitle, onValueChange = {
            text -> inputTitle = text
        }, label = {
            Text(text = "Please enter book title")
        })

        Button(onClick = {
            if(inputTitle.isNotEmpty()) {
                viewModel.addBook(BookEntity(0, inputTitle))
            }
        }) {
            Text(text = "Insert book into DB")
        }

        BooksList(viewModel, navController)
    }
}

@Composable
fun BooksList(viewModel: BooksViewModel, navController: NavHostController) {
    val books by viewModel.books.collectAsState(initial = emptyList())
    LazyColumn() {
        items(items = books) {
            item -> BookCard(viewModel = viewModel, book = item, navController)
        }
    }
}

@Composable
fun BookCard(viewModel: BooksViewModel, book: BookEntity, navController: NavHostController) {
    Card(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()) {
        Row {
            Text("${book.id}", fontSize = 22.sp, modifier = Modifier.padding(start = 4.dp, end = 4.dp))
            Text(book.title, fontSize = 22.sp)
            IconButton(onClick = {
                viewModel.deleteBook(book)
            }) {
              Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }

            IconButton(onClick = {
                navController.navigate("UpdateScreen/${book.id}")
                //viewModel.updateBook(book)
            }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }

        }
    }
}