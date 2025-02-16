package com.bookstore_service.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*


data class CategoryResponseDTO(
    val id: UUID,
    @Schema(example = "Roman")
    val name: String,
    @Schema(example = "1")
    val booksCount: Int = 0,
)