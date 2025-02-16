package com.bookstore_service.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.util.UUID


data class BookCreateRequestDTO(
    @get:NotBlank(message = "BookCreateRequestDTO.title is required")
    @Schema(example = "Twisted lies")
    var title: String,
    var categoryId: UUID?,
    @Schema(example = "350")
    var pages: Int?,
    @field:Min(0) @field:Max(5)
    var spice: Int?,
)
