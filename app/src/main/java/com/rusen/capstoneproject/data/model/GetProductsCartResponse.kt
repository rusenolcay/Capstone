package com.rusen.capstoneproject.data.model

data class GetProductsCartResponse(
    val products: List<Product>,
    val status: Int,
    val message: String,
)
