package com.sahil.bookapplication.repository

import com.sahil.bookapplication.room.BookEntity
import com.sahil.bookapplication.room.BooksDB

class Repository (val booksDb: BooksDB) {
    suspend fun addBookToRoom(bookEntity: BookEntity) {
        booksDb.bookDao().addBook(bookEntity)
    }

    fun getAllBooks() = booksDb.bookDao().getAllBooks()

    suspend fun deleteBook(bookEntity: BookEntity) {
        booksDb.bookDao().deleteBook(bookEntity)
    }

    suspend fun updateBook(bookEntity: BookEntity) {
        booksDb.bookDao().updateBook(bookEntity)
    }
}