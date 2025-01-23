package controllers

import (
	"cs6650/models"
	"cs6650/services"
	"net/http"

	"github.com/gin-gonic/gin"
)

// GetBooks handles GET requests to retrieve all books
func GetBooks(c *gin.Context) {
	books := services.GetAllBooks()
	c.JSON(http.StatusOK, books)
}

// AddBook handles POST requests to add a new book
func AddBook(c *gin.Context) {
	var newBook models.Book
	if err := c.ShouldBindJSON(&newBook); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	services.AddBook(newBook)
	c.JSON(http.StatusCreated, newBook)
}
