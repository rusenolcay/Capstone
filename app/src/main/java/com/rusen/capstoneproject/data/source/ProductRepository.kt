package com.rusen.capstoneproject.data.source

import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.local.ProductLocalDataSource
import com.rusen.capstoneproject.data.source.remote.ProductRemoteDataSource

class ProductRepository {

    private val localDataSource = ProductLocalDataSource()
    private val remoteDataSource = ProductRemoteDataSource()

    fun getProductDetail(
        id: Long,
        onSuccess: (Product) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        return remoteDataSource.getProductDetail(
            id = id,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    fun getProducts(
        onSuccess: (List<Product>?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        remoteDataSource.getProducts(onSuccess, onFailure)
    }

    fun getProductsByQuery(
        query: String,
        onSuccess: (List<Product>?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        remoteDataSource.getProductsByQuery(query, onSuccess, onFailure)
    }

    fun getFavoriteProduct(id: Long): Product? {
        return localDataSource.getProduct(id)
    }

    fun deleteFavoriteProduct(product: Product) {
        return localDataSource.deleteProduct(product)
    }

    fun addFavoriteProduct(product: Product) {
        localDataSource.addProduct(product)
    }

    fun deleteFavoriteProductById(id: Long) {
        localDataSource.deleteByProductId(id)
    }

    fun deleteAllFavoriteProducts() {
        localDataSource.deleteAllProducts()
    }

    fun getFavoriteProducts(): List<Product>? {
        return localDataSource.getProducts()
    }
}