package com.bookstore_service.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class CategoryCreateRequestDTO(
    @get:NotBlank(message = "CategoryCreateRequestDTO.name is required")
    @Schema(example = "Roman")
    var name: String,
)