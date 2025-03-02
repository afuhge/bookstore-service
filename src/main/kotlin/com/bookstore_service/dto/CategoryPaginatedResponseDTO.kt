package com.bookstore_service.dto

import io.swagger.v3.oas.annotations.media.Schema


data class CategoryPaginatedResponseDTO(
    val data : List<CategoryResponseDTO> =  mutableListOf(),
    @Schema(example = "1")
    val pageNumber: Int = 1,
    @Schema(example = "10")
    val pageSize: Int = 10,
    @Schema(example = "1")
    val totalEntries: Int = 0,
    @Schema(example = "1")
    val totalPages: Int = 1
)