package services

import "cs6650/models"

// Fake in-memory database
var books = []models.Book{
	{ID: 1, Title: "The Go Programming Language", Author: "Alan Donovan", Price: 45.99},
	{ID: 2, Title: "Clean Code", Author: "Robert Martin", Price: 39.99},
}

// GetAllBooks retrieves all books
func GetAllBooks() []models.Book {
	return books
}

// AddBook adds a new book to the list
func AddBook(newBook models.Book) {
	books = append(books, newBook)
}
