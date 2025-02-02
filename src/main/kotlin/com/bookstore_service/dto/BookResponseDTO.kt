package com.bookstore_service.dto

import java.util.UUID

data class BookResponseDTO(
    val id: UUID,
    val title: String,
    val category: CategoryResponseDTO?,
    val pages: Int?,
    val spice: Int?,
)
