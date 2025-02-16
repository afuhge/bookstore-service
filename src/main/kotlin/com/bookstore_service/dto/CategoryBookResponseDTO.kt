package com.bookstore_service.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

data class CategoryBookResponseDTO(
    val id: UUID,
    @Schema(example = "Roman")
    val name: String,
)