package com.sahil.bookapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sahil.bookapplication.room.BookEntity
import com.sahil.bookapplication.viewmodel.BooksViewModel

@Composable
fun UpdateScreen(viewModel: BooksViewModel, bookID: String?) {
    var updateTitle by remember {
        mutableStateOf("")
    }
    Column {
        OutlinedTextField(value = updateTitle, onValueChange = {
            text -> updateTitle = text
        },
            label = { Text(text = "Update Book Name")},
            placeholder = {
                Text(text = "Enter New Book Title")
            })

        Button(onClick = {
            viewModel.updateBook(BookEntity(id = bookID!!.toInt(), title = updateTitle))
        }) {
            Text("Update Title")
        }

    }
}