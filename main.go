package main

import (
	"cs6650/controllers"

	"github.com/gin-gonic/gin"
)

func main() {
	// fmt.Println("Hello, CS6650!")
	// fmt.Println(quote.Go())
	r := gin.Default()

	// Define API routes
	r.GET("/books", controllers.GetBooks)
	r.POST("/books", controllers.AddBook)

	// Run the server (the default port is 8080)
	r.Run(":8080")
}
