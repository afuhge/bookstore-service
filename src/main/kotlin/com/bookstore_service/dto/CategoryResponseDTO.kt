package com.bookstore_service.dto

import java.util.*


data class CategoryResponseDTO(
    val id: UUID,
    val name: String,
    val books: MutableList<BookResponseDTO>? = null,
)