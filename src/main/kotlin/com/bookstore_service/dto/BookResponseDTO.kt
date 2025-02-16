package com.bookstore_service.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.util.UUID

data class BookResponseDTO(
    val id: UUID,
    @Schema(example = "Twisted lies")
    val title: String,
    val category: CategoryResponseDTO?,
    @Schema(example = "350")
    val pages: Int?,
    @field:Min(0) @field:Max(5)
    val spice: Int?,
)
