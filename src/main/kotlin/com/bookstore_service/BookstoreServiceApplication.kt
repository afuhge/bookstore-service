package com.bookstore_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookstoreServiceApplication

fun main(args: Array<String>) {
	runApplication<BookstoreServiceApplication>(*args)
}
