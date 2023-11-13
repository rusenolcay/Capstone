package com.rusen.capstoneproject.data.source.remote

import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val service: ProductService
) {

    suspend fun getProducts() = service.getProducts()

    suspend fun getProductDetail(id: Long) = service.getProductDetail(id)

    suspend fun getProductsByQuery(query: String) = service.getProductsByQuery(query)
}