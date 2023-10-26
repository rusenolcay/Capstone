package com.rusen.capstoneproject.data.model

data class Product(
    val id: Long? = null,
    val title: String? = null,
    val price: Double? = null,
    val salePrice: Double? = null,
    val description: String? = null,
    val category: String? = null,
    val imageOne: String? = null,
    val imageTwo: String? = null,
    val imageThree: String? = null,
    val rate: Double? = null,
    val count: Int? = null,
    val saleState: Boolean? = false,
)