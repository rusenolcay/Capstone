package com.rusen.capstoneproject.data.model

data class GetProductDetailResponse(
    val product: Product? = null,
    val status: Int? = null,
    val message: String? = null
)
