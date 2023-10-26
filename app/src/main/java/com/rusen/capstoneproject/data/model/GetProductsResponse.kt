package com.rusen.capstoneproject.data.model

data class GetProductsResponse(
    val products: List<Product>? = null,
    val status: Int? = null,
    val message: String? = null
)