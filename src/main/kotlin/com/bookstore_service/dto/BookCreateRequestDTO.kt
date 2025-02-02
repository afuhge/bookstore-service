package com.bookstore_service.dto

import jakarta.validation.constraints.NotBlank
import java.util.UUID


data class BookCreateRequestDTO(
    @get:NotBlank(message = "BookCreateRequestDTO.title is required")
    var title: String,
    var categoryId: UUID?,
    var pages: Int?,
    var spice: Int?,
)
