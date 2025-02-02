package com.bookstore_service.dto

import jakarta.validation.constraints.NotBlank

data class CategoryCreateRequestDTO(
    @get:NotBlank(message = "CategoryCreateRequestDTO.name is required")
    var name: String,
)