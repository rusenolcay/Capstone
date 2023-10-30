package com.rusen.capstoneproject.data.model

data class GetProductDetailResponse(
    val product:Product?,
    val status:Int,
    val message: String?
)
