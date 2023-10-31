package com.rusen.capstoneproject.data.model

data class GetProductSearchResponse(
    val products: List<Product>? = null,
    val status: Int? = null,
    val message: String? = null
)